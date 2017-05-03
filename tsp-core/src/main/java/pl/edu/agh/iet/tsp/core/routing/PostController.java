package pl.edu.agh.iet.tsp.core.routing;

import org.apache.http.client.utils.URIBuilder;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.iet.tsp.core.config.ApplicationConfig.AppConfig;
import pl.edu.agh.iet.tsp.core.domain.Post;
import pl.edu.agh.iet.tsp.core.routing.json.PagedResult;
import pl.edu.agh.iet.tsp.core.routing.json.PostCreation;
import pl.edu.agh.iet.tsp.core.service.PostService;

import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static pl.edu.agh.iet.tsp.core.util.DateTimeUtils.*;

/**
 * @author Wojciech Pachuta.
 */
@RestController
public class PostController {

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private PostService postService;

    @RequestMapping(value = "/posts", method = RequestMethod.POST)
    public void addPost(
            @RequestParam String authorId,
            @RequestParam(required = false) String category,
            @RequestBody PostCreation postCreation) {
        Post post = new Post(new ObjectId(authorId), utcNow(), postCreation.title, category, postCreation.content);
        postService.addPost(post);
    }


    @RequestMapping(value = "/posts/latest", method = RequestMethod.GET)
    public PagedResult getLatestPosts(
            @RequestParam Integer number,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Long after) throws URISyntaxException {
        List<Post> result;
        if (category != null && after != null) {
            result = postService.getPageOfPostsInCategoryBefore(category, number, utcLocalDateTime(after));
        } else if (category != null) {
            result = postService.getFirstPageOfPostsInCategory(category, number);
        } else if (after != null) {
            result = postService.getPageOfPostsBefore(number, utcLocalDateTime(after));
        } else {
            result = postService.getFirstPageOfPosts(number);
        }

        Optional<LocalDateTime> lastPostInPageCreationTime = Optional.of(result)
                .filter(x -> x.size() > 0)
                .map(x -> x.get(x.size() - 1).getCreationTime());

        boolean existsNextPage;
        if (category != null) {
            existsNextPage = lastPostInPageCreationTime.map(x -> postService.existsNextPage(category, x)).orElse(false);
        } else {
            existsNextPage = lastPostInPageCreationTime.map(postService::existsNextPage).orElse(false);
        }

        String next = null;
        if (existsNextPage) {
            URIBuilder uriBuilder = new URIBuilder(appConfig.domain);
            uriBuilder.setPath("posts/latest");
            uriBuilder.setParameter("number", number.toString());
            uriBuilder.addParameter("after", utcMillis(lastPostInPageCreationTime.get()).toString());
            if (category != null) uriBuilder.addParameter("category", category);
            next = uriBuilder.toString();
        }

        return new PagedResult<>(result, next);
    }

}

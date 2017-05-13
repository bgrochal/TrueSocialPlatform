package pl.edu.agh.iet.tsp.web.controller;

import org.apache.http.client.utils.URIBuilder;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.iet.tsp.web.config.ApplicationConfig.AppConfig;
import pl.edu.agh.iet.tsp.web.controller.json.IdWrapper;
import pl.edu.agh.iet.tsp.web.controller.json.PagedResult;
import pl.edu.agh.iet.tsp.web.controller.json.PostCreation;
import pl.edu.agh.iet.tsp.database.domain.Post;
import pl.edu.agh.iet.tsp.service.PostService;
import pl.edu.agh.iet.tsp.service.exception.NoSuchPostException;

import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static pl.edu.agh.iet.tsp.service.util.DateTimeUtils.*;

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
    public IdWrapper addPost(
            @RequestParam String authorId,
            @RequestBody PostCreation postCreation) {
        Post post = new Post(new ObjectId(authorId), utcNow(), postCreation.title, postCreation.category, postCreation.content);
        return new IdWrapper(postService.addPost(post));
    }

    @RequestMapping(value = "/posts/{postId}", method = RequestMethod.PUT)
    public void modifyPost(
            @RequestParam String authorId, //this will probably be necessary for auth
            @PathVariable("postId") String postId,
            @RequestBody PostCreation postCreation) throws NoSuchPostException {
        Optional<Post> oldPost = postService.getPost(new ObjectId(authorId), new ObjectId(postId));
        if (!oldPost.isPresent()) {
            throw new NoSuchPostException();
        }

        Post newPost = new Post(
                new ObjectId(postId),
                new ObjectId(authorId),
                oldPost.get().getCreationTime(),
                postCreation.title,
                postCreation.category,
                postCreation.content,
                utcNow());

        postService.updatePost(newPost);
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
            existsNextPage = lastPostInPageCreationTime.map(x -> postService.existsNextPageInCategory(category, x)).orElse(false);
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

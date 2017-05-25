package pl.edu.agh.iet.tsp.web.controller;

import org.apache.http.client.utils.URIBuilder;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.iet.tsp.database.domain.Comment;
import pl.edu.agh.iet.tsp.service.CommentService;
import pl.edu.agh.iet.tsp.service.UserService;
import pl.edu.agh.iet.tsp.service.exception.NoSuchCommentException;
import pl.edu.agh.iet.tsp.web.config.ApplicationConfig;
import pl.edu.agh.iet.tsp.web.controller.json.CommentCreation;
import pl.edu.agh.iet.tsp.web.controller.json.CommentWithUsername;
import pl.edu.agh.iet.tsp.web.controller.json.IdWrapper;
import pl.edu.agh.iet.tsp.web.controller.json.PagedResult;

import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static pl.edu.agh.iet.tsp.service.util.DateTimeUtils.*;

/**
 * @author Wojciech Pachuta.
 */
@RestController
public class CommentController {

    @Autowired
    private ApplicationConfig.AppConfig appConfig;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/comments", method = RequestMethod.POST)
    public IdWrapper addComment(
            @RequestParam String authorId,
            @RequestParam String postId,
            @RequestBody CommentCreation commentCreation) {
        Comment comment = new Comment(new ObjectId(authorId), new ObjectId(postId), utcNow(), commentCreation.content);
        return new IdWrapper(commentService.addComment(comment));
    }

    @RequestMapping(value = "/comments/{commentId}", method = RequestMethod.PUT)
    public void modifyComment(
            @RequestParam String authorId, //this will probably be necessary for auth
            @RequestParam String postId,
            @PathVariable("commentId") String commentId,
            @RequestBody CommentCreation commentCreation) throws NoSuchCommentException {
        Optional<Comment> oldComment = commentService.getComment(new ObjectId(authorId), new ObjectId(commentId));
        if (!oldComment.isPresent()) {
            throw new NoSuchCommentException();
        }

        Comment newComment = new Comment(
                new ObjectId(commentId),
                new ObjectId(authorId),
                new ObjectId(postId),
                oldComment.get().getCreationTime(),
                commentCreation.content,
                utcNow());

        commentService.updateComment(newComment);
    }


    @RequestMapping(value = "/comments/latest", method = RequestMethod.GET)
    public PagedResult<CommentWithUsername> getLatestComments(
            @RequestParam String postId,
            @RequestParam Integer number,
            @RequestParam(required = false) Long after) throws URISyntaxException {
        List<Comment> result;
        if (after != null) {
            result = commentService.getPageOfCommentsBefore(new ObjectId(postId), number, utcLocalDateTime(after));
        } else {
            result = commentService.getFirstPageOfComments(new ObjectId(postId), number);
        }

        Optional<LocalDateTime> lastCommentInPageCreationTime = Optional.of(result)
                .filter(x -> x.size() > 0)
                .map(x -> x.get(x.size() - 1).getCreationTime());

        boolean existsNextPage = lastCommentInPageCreationTime
                .map(x -> commentService.existsNextPage(new ObjectId(postId), x))
                .orElse(false);

        String next = null;
        if (existsNextPage) {
            URIBuilder uriBuilder = new URIBuilder(appConfig.domain);
            uriBuilder.setPath("comments/latest");
            uriBuilder.setParameter("number", number.toString());
            uriBuilder.setParameter("postId", postId);
            uriBuilder.addParameter("after", utcMillis(lastCommentInPageCreationTime.get()).toString());
            next = uriBuilder.toString();
        }

        return new PagedResult<>(
                result.stream().map(x -> new CommentWithUsername(x, userService.getUsername(x.getAuthorId()).orElse(null))).collect(Collectors.toList()),
                next
        );
    }

    @RequestMapping(value = "/comments", method = RequestMethod.GET)
    public PagedResult<CommentWithUsername> getAllCommentsByUser(
            @RequestParam String authorId,
            @RequestParam Integer number,
            @RequestParam(required = false) Long after) throws URISyntaxException {
        List<Comment> result;
        if (after != null) {
            result = commentService.getPageOfCommentsByUserBefore(new ObjectId(authorId), number, utcLocalDateTime(after));
        } else {
            result = commentService.getFirstPageOfCommentsByUser(new ObjectId(authorId), number);
        }

        Optional<LocalDateTime> lastPostInPageCreationTime = Optional.of(result)
                .filter(x -> x.size() > 0)
                .map(x -> x.get(x.size() - 1).getCreationTime());

        boolean existsNextPage = lastPostInPageCreationTime.map(x -> commentService.existsNextPageByUser(new ObjectId(authorId), x)).orElse(false);

        String next = null;
        if (existsNextPage) {
            URIBuilder uriBuilder = new URIBuilder(appConfig.domain);
            uriBuilder.setPath("/comments");
            uriBuilder.setParameter("number", number.toString());
            uriBuilder.setParameter("authorId", authorId);
            uriBuilder.addParameter("after", utcMillis(lastPostInPageCreationTime.get()).toString());
            next = uriBuilder.toString();
        }

        return new PagedResult<>(
                result.stream().map(x -> new CommentWithUsername(x, userService.getUsername(x.getAuthorId()).orElse(null))).collect(Collectors.toList()),
                next
        );

    }

    @RequestMapping(value = "/comments/{commentId}", method = RequestMethod.DELETE)
    public void deleteComment(@PathVariable("commentId") String commentId) {
        commentService.removeComment(new ObjectId(commentId));
    }


}

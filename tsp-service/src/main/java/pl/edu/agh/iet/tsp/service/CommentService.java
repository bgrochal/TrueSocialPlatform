package pl.edu.agh.iet.tsp.service;

import org.bson.types.ObjectId;
import pl.edu.agh.iet.tsp.database.domain.Comment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author Wojciech Pachuta.
 */
public interface CommentService {
    ObjectId addComment(Comment comment);

    Optional<Comment> getComment(ObjectId authorId, ObjectId commentId);

    void updateComment(Comment comment);

    List<Comment> getFirstPageOfComments(ObjectId postId, Integer number);

    List<Comment> getPageOfCommentsBefore(ObjectId postId, Integer number, LocalDateTime dateTime);

    boolean existsNextPage(ObjectId postId, LocalDateTime dateTime);
}

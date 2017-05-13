package pl.edu.agh.iet.tsp.database.db;

import org.bson.types.ObjectId;
import org.mongodb.morphia.dao.DAO;
import pl.edu.agh.iet.tsp.database.domain.Comment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Wojciech Pachuta.
 */
public interface CommentDao extends DAO<Comment, UUID> {
    Optional<Comment> getComment(ObjectId authorId, ObjectId commentId);

    List<Comment> getLatestComments(ObjectId postId, Integer number);

    List<Comment> getLatestCommentsBefore(ObjectId postId, Integer number, LocalDateTime dateTime);

    boolean existsNextPage(ObjectId postId, LocalDateTime dateTime);
}

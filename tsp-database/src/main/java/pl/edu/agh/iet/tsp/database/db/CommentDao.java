package pl.edu.agh.iet.tsp.database.db;

import org.bson.types.ObjectId;
import org.mongodb.morphia.dao.DAO;
import pl.edu.agh.iet.tsp.database.domain.Comment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author Wojciech Pachuta.
 */
public interface CommentDao extends DAO<Comment, ObjectId> {
    Optional<Comment> getComment(ObjectId authorId, ObjectId commentId);

    List<Comment> getLatestComments(ObjectId postId, Integer number);

    List<Comment> getLatestCommentsBefore(ObjectId postId, Integer number, LocalDateTime dateTime);

    boolean existsNextPage(ObjectId postId, LocalDateTime dateTime);

    void deleteAllCommentsOfPost(ObjectId objectId);

    List<Comment> getPageOfCommentsByUserBefore(ObjectId authorId, Integer number, LocalDateTime dateTime);

    List<Comment> getFirstPageOfCommentsByUser(ObjectId authorId, Integer number);

    boolean existsNextPageByUser(ObjectId authorId, LocalDateTime dateTime);

    void deleteAllByAuthor(ObjectId authorId);
}

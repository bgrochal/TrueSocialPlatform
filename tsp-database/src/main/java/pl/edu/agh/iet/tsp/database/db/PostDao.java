package pl.edu.agh.iet.tsp.database.db;

import org.bson.types.ObjectId;
import org.mongodb.morphia.dao.DAO;
import pl.edu.agh.iet.tsp.database.domain.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author Wojciech Pachuta.
 */
public interface PostDao extends DAO<Post, ObjectId> {

    List<Post> findByAuthorId(ObjectId authorId);

    /**
     * Result is sorted from latest to oldest.
     */
    List<Post> getLatestPosts(int number);

    /**
     * Result is sorted from latest to oldest.
     */
    List<Post> getLatestPostsBefore(int number, LocalDateTime dateTime);

    /**
     * Result is sorted from latest to oldest.
     */
    List<Post> getLatestPostsInCategory(String category, int number);

    /**
     * Result is sorted from latest to oldest.
     */
    List<Post> getLatestPostsInCategoryBefore(String category, int number, LocalDateTime dateTime);

    boolean existsNextPage(LocalDateTime after);

    boolean existsNextPageInCategory(String category, LocalDateTime dateTime);

    Optional<Post> getPost(ObjectId authorId, ObjectId postId);

    List<Post> getFirstPageOfPostsByUser(ObjectId authorId, int number);

    List<Post> getPageOfPostsByUserBefore(ObjectId authorId, int number, LocalDateTime dateTime);

    boolean existsNextPageByUser(ObjectId authorId, LocalDateTime after);

    void deleteAllByAuthor(ObjectId authorId);
}

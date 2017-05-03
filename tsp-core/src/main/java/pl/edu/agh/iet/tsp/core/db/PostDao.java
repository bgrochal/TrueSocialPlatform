package pl.edu.agh.iet.tsp.core.db;

import org.mongodb.morphia.dao.DAO;
import pl.edu.agh.iet.tsp.core.domain.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @author Wojciech Pachuta.
 */
public interface PostDao extends DAO<Post, UUID> {

    List<Post> findByAuthorId(UUID authorId);

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

    boolean existsNextPage(String category, LocalDateTime dateTime);

}

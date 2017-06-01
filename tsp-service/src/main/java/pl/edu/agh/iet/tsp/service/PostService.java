package pl.edu.agh.iet.tsp.service;

import org.bson.types.ObjectId;
import pl.edu.agh.iet.tsp.database.domain.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author Wojciech Pachuta.
 */
public interface PostService {
    List<Post> getFirstPageOfPosts(int number);

    List<Post> getPageOfPostsBefore(int number, LocalDateTime dateTime);

    List<Post> getFirstPageOfPostsByUser(ObjectId authorId, int number);

    List<Post> getPageOfPostsByUserBefore(ObjectId authorId, int number, LocalDateTime dateTime);

    List<Post> getFirstPageOfPostsInCategory(String category, int number);

    List<Post> getPageOfPostsInCategoryBefore(String category, int number, LocalDateTime dateTime);

    boolean existsNextPage(LocalDateTime after);

    boolean existsNextPageByUser(ObjectId authorId, LocalDateTime after);

    boolean existsNextPageInCategory(String category, LocalDateTime after);

    Optional<Post> getPost(ObjectId authorId, ObjectId postId);

    ObjectId addPost(Post post);

    void updatePost(Post post);

    void removePostAndAllItsComments(ObjectId objectId);
}

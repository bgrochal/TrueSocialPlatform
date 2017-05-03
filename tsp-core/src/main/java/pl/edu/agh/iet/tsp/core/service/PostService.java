package pl.edu.agh.iet.tsp.core.service;

import pl.edu.agh.iet.tsp.core.domain.Post;

import java.util.List;
import java.time.LocalDateTime;

/**
 * @author Wojciech Pachuta.
 */
public interface PostService {
    List<Post> getFirstPageOfPosts(int number);

    List<Post> getPageOfPostsBefore(int number, LocalDateTime dateTime);

    List<Post> getFirstPageOfPostsInCategory(String category, int number);

    List<Post> getPageOfPostsInCategoryBefore(String category, int number, LocalDateTime dateTime);

    boolean existsNextPage(LocalDateTime after);

    boolean existsNextPage(String category, LocalDateTime after);

    void addPost(Post post);
}

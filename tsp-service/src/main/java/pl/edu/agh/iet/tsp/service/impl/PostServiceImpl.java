package pl.edu.agh.iet.tsp.service.impl;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.iet.tsp.database.db.PostDao;
import pl.edu.agh.iet.tsp.database.domain.Post;
import pl.edu.agh.iet.tsp.service.PostService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author Wojciech Pachuta.
 */
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostDao postDao;

    @Override
    public List<Post> getFirstPageOfPosts(int number) {
        return postDao.getLatestPosts(number);
    }

    @Override
    public List<Post> getPageOfPostsBefore(int number, LocalDateTime dateTime) {
        return postDao.getLatestPostsBefore(number, dateTime);
    }

    @Override
    public List<Post> getFirstPageOfPostsInCategory(String category, int number) {
        return postDao.getLatestPostsInCategory(category, number);
    }

    @Override
    public List<Post> getPageOfPostsInCategoryBefore(String category, int number, LocalDateTime dateTime) {
        return postDao.getLatestPostsInCategoryBefore(category, number, dateTime);
    }

    @Override
    public boolean existsNextPage(LocalDateTime dateTime) {
        return postDao.existsNextPage(dateTime);
    }

    @Override
    public boolean existsNextPageInCategory(String category, LocalDateTime dateTime) {
        return postDao.existsNextPage(category, dateTime);
    }

    @Override
    public Optional<Post> getPost(ObjectId authorId, ObjectId postId) {
        return postDao.getPost(authorId, postId);
    }

    @Override
    public ObjectId addPost(Post post) {
        postDao.save(post);
        return post.getId();
    }

    @Override
    public void updatePost(Post post) {
        postDao.save(post);
    }
}

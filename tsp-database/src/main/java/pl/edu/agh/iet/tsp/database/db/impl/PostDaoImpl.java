package pl.edu.agh.iet.tsp.database.db.impl;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.FindOptions;
import org.mongodb.morphia.query.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.edu.agh.iet.tsp.database.db.PostDao;
import pl.edu.agh.iet.tsp.database.domain.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author Wojciech Pachuta.
 */
@Repository
public class PostDaoImpl extends BasicDAO<Post, ObjectId> implements PostDao {

    @Autowired
    public PostDaoImpl(Datastore datastore) {
        super(datastore);
    }

    @Override
    public List<Post> findByAuthorId(ObjectId authorId) {
        return createQuery()
                .field(Post.AUTHOR_ID).equal(authorId)
                .asList();
    }

    @Override
    public List<Post> getLatestPosts(int number) {
        return createQuery()
                .order(Sort.descending(Post.CREATION_TIME))
                .asList(new FindOptions().limit(number));
    }

    @Override
    public List<Post> getLatestPostsBefore(int number, LocalDateTime dateTime) {
        return createQuery()
                .field(Post.CREATION_TIME).lessThan(dateTime)
                .order(Sort.descending(Post.CREATION_TIME))
                .asList(new FindOptions().limit(number));
    }

    @Override
    public List<Post> getLatestPostsInCategory(String category, int number) {
        return createQuery()
                .field(Post.CATEGORY).equal(category)
                .order(Sort.descending(Post.CREATION_TIME))
                .asList(new FindOptions().limit(number));
    }

    @Override
    public List<Post> getLatestPostsInCategoryBefore(String category, int number, LocalDateTime dateTime) {
        return createQuery()
                .field(Post.CATEGORY).equal(category)
                .field(Post.CREATION_TIME).lessThan(dateTime)
                .order(Sort.descending(Post.CREATION_TIME))
                .asList(new FindOptions().limit(number));
    }

    @Override
    public boolean existsNextPage(LocalDateTime dateTime) {
        return null != createQuery()
                .field(Post.CREATION_TIME).lessThan(dateTime)
                .get();
    }

    @Override
    public boolean existsNextPageInCategory(String category, LocalDateTime dateTime) {
        return null != createQuery()
                .field(Post.CATEGORY).equal(category)
                .field(Post.CREATION_TIME).lessThan(dateTime)
                .get();
    }

    @Override
    public Optional<Post> getPost(ObjectId authorId, ObjectId postId) {
        return Optional.ofNullable(createQuery()
                .field(Post.AUTHOR_ID).equal(authorId)
                .field("_id").equal(postId)
                .get());
    }

    @Override
    public Optional<Post> getPost(ObjectId postId) {
        return Optional.ofNullable(createQuery()
                .field("_id").equal(postId)
                .get());
    }

    @Override
    public List<Post> getFirstPageOfPostsByUser(ObjectId authorId, int number) {
        return createQuery()
                .field(Post.AUTHOR_ID).equal(authorId)
                .order(Sort.descending(Post.CREATION_TIME))
                .asList(new FindOptions().limit(number));
    }

    @Override
    public List<Post> getPageOfPostsByUserBefore(ObjectId authorId, int number, LocalDateTime dateTime) {
        return createQuery()
                .field(Post.AUTHOR_ID).equal(authorId)
                .field(Post.CREATION_TIME).lessThan(dateTime)
                .order(Sort.descending(Post.CREATION_TIME))
                .asList(new FindOptions().limit(number));
    }

    @Override
    public boolean existsNextPageByUser(ObjectId authorId, LocalDateTime dateTime) {
        return null != createQuery()
                .field(Post.AUTHOR_ID).equal(authorId)
                .field(Post.CREATION_TIME).lessThan(dateTime)
                .get();
    }

    @Override
    public void deleteAllByAuthor(ObjectId authorId) {
        getDatastore().delete(createQuery().field(Post.AUTHOR_ID).equal(authorId));
    }

    @Override
    public List<Post> getAllByAuthor(ObjectId authorId) {
        return createQuery()
                .field(Post.AUTHOR_ID).equal(authorId)
                .asList();
    }
}

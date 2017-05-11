package pl.edu.agh.iet.tsp.core.db.impl;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.FindOptions;
import org.mongodb.morphia.query.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.edu.agh.iet.tsp.core.db.CommentDao;
import pl.edu.agh.iet.tsp.core.domain.Comment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Wojciech Pachuta.
 */
@Repository
public class CommentDaoImpl extends BasicDAO<Comment, UUID> implements CommentDao {

    @Autowired
    public CommentDaoImpl(Datastore datastore) {
        super(datastore);
    }

    @Override
    public Optional<Comment> getComment(ObjectId authorId, ObjectId commentId) {
        return Optional.ofNullable(createQuery()
                .field(Comment.AUTHOR_ID).equal(authorId)
                .field("_id").equal(commentId)
                .get()
        );
    }

    @Override
    public List<Comment> getLatestComments(ObjectId postId, Integer number) {
        return createQuery()
                .field(Comment.POST_ID).equal(postId)
                .order(Sort.descending(Comment.CREATION_TIME))
                .asList(new FindOptions().limit(number));
    }

    @Override
    public List<Comment> getLatestCommentsBefore(ObjectId postId, Integer number, LocalDateTime dateTime) {
        return createQuery()
                .field(Comment.POST_ID).equal(postId)
                .field(Comment.CREATION_TIME).lessThan(dateTime)
                .order(Sort.descending(Comment.CREATION_TIME))
                .asList(new FindOptions().limit(number));
    }

    @Override
    public boolean existsNextPage(ObjectId postId, LocalDateTime dateTime) {
        return null != createQuery()
                .field(Comment.POST_ID).equal(postId)
                .field(Comment.CREATION_TIME).lessThan(dateTime)
                .get();
    }
}

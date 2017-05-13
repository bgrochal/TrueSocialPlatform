package pl.edu.agh.iet.tsp.service.impl;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.iet.tsp.database.db.CommentDao;
import pl.edu.agh.iet.tsp.database.domain.Comment;
import pl.edu.agh.iet.tsp.service.CommentService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author Wojciech Pachuta.
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Override
    public ObjectId addComment(Comment comment) {
        commentDao.save(comment);
        return comment.getId();
    }

    @Override
    public Optional<Comment> getComment(ObjectId authorId, ObjectId commentId) {
        return commentDao.getComment(authorId, commentId);
    }

    @Override
    public void updateComment(Comment comment) {
        commentDao.save(comment);
    }

    @Override
    public List<Comment> getFirstPageOfComments(ObjectId postId, Integer number) {
        return commentDao.getLatestComments(postId, number);
    }

    @Override
    public List<Comment> getPageOfCommentsBefore(ObjectId postId, Integer number, LocalDateTime dateTime) {
        return commentDao.getLatestCommentsBefore(postId, number, dateTime);
    }

    @Override
    public boolean existsNextPage(ObjectId postId, LocalDateTime dateTime) {
        return commentDao.existsNextPage(postId, dateTime);
    }
}

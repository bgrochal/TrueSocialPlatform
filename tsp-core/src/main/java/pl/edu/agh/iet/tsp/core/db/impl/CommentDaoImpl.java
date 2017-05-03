package pl.edu.agh.iet.tsp.core.db.impl;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import pl.edu.agh.iet.tsp.core.db.CommentDao;
import pl.edu.agh.iet.tsp.core.domain.Comment;

import java.util.UUID;

/**
 * @author Wojciech Pachuta.
 */
public class CommentDaoImpl extends BasicDAO<Comment, UUID> implements CommentDao {

    public CommentDaoImpl(Datastore datastore) {
        super(datastore);
    }

}

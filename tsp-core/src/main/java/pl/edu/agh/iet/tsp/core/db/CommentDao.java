package pl.edu.agh.iet.tsp.core.db;

import org.mongodb.morphia.dao.DAO;
import pl.edu.agh.iet.tsp.core.domain.Comment;

import java.util.UUID;

/**
 * @author Wojciech Pachuta.
 */
public interface CommentDao extends DAO<Comment, UUID> {
}

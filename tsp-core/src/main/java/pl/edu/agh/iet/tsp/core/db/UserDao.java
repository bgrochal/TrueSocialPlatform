package pl.edu.agh.iet.tsp.core.db;

import org.mongodb.morphia.dao.DAO;
import pl.edu.agh.iet.tsp.core.domain.User;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Wojciech Pachuta.
 */
public interface UserDao extends DAO<User, UUID>{

    Optional<User> findByUsername(String username);
}

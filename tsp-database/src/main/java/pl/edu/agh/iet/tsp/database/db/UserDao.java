package pl.edu.agh.iet.tsp.database.db;

import org.bson.types.ObjectId;
import org.mongodb.morphia.dao.DAO;
import pl.edu.agh.iet.tsp.database.domain.AuthenticationData;
import pl.edu.agh.iet.tsp.database.domain.User;

import java.util.Optional;

/**
 * @author Wojciech Pachuta.
 */
public interface UserDao extends DAO<User, ObjectId>{

    Optional<User> findByUsername(String username);

    Optional<User> findByAuthenticationData(AuthenticationData authenticationData);

}

package pl.edu.agh.iet.tsp.database.db;

import org.mongodb.morphia.dao.DAO;
import pl.edu.agh.iet.tsp.database.domain.AuthenticationData;
import pl.edu.agh.iet.tsp.database.domain.User;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Wojciech Pachuta.
 */
public interface UserDao extends DAO<User, UUID>{

    Optional<User> findByUsername(String username);

    Optional<User> findByAuthenticationData(AuthenticationData authenticationData);

}

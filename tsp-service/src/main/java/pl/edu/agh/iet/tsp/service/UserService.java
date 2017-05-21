package pl.edu.agh.iet.tsp.service;

import org.bson.types.ObjectId;
import pl.edu.agh.iet.tsp.database.domain.AuthenticationData;
import pl.edu.agh.iet.tsp.database.domain.User;
import pl.edu.agh.iet.tsp.service.exception.DuplicateUsernameException;

import java.util.Optional;

/**
 * @author Wojciech Pachuta.
 */
public interface UserService {

    Optional<User> getUserByName(String username);

    Optional<User> getUserByAuthenticationData(AuthenticationData authenticationData);

    ObjectId addUser(User user) throws DuplicateUsernameException;

}

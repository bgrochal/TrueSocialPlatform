package pl.edu.agh.iet.tsp.service;

import org.bson.types.ObjectId;
import pl.edu.agh.iet.tsp.database.domain.User;
import pl.edu.agh.iet.tsp.service.exception.DuplicateUsernameException;

/**
 * @author Wojciech Pachuta.
 */
public interface UserService {
    ObjectId addUser(User user) throws DuplicateUsernameException;
}

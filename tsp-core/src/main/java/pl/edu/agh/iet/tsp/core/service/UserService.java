package pl.edu.agh.iet.tsp.core.service;

import org.bson.types.ObjectId;
import pl.edu.agh.iet.tsp.core.domain.User;
import pl.edu.agh.iet.tsp.core.service.exception.DuplicateUsernameException;

/**
 * @author Wojciech Pachuta.
 */
public interface UserService {
    ObjectId addUser(User user) throws DuplicateUsernameException;
}

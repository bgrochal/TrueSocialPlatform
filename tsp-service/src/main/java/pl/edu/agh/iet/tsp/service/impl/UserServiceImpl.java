package pl.edu.agh.iet.tsp.service.impl;

import com.mongodb.DuplicateKeyException;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.iet.tsp.database.db.UserDao;
import pl.edu.agh.iet.tsp.database.domain.User;
import pl.edu.agh.iet.tsp.service.UserService;
import pl.edu.agh.iet.tsp.service.exception.DuplicateUsernameException;

/**
 * @author Wojciech Pachuta.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public ObjectId addUser(User user) throws DuplicateUsernameException {
        try {
            userDao.save(user);
        } catch(DuplicateKeyException e) {
            throw new DuplicateUsernameException(e);

        }
        return user.getId();
    }
}

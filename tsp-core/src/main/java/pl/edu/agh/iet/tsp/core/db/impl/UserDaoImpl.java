package pl.edu.agh.iet.tsp.core.db.impl;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import pl.edu.agh.iet.tsp.core.db.UserDao;
import pl.edu.agh.iet.tsp.core.domain.User;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Wojciech Pachuta.
 */
public class UserDaoImpl extends BasicDAO<User, UUID> implements UserDao {

    public UserDaoImpl(Datastore datastore) {
        super(datastore);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        User resultNullable = createQuery()
                .field(User.USERNAME).equal(username)
                .get();
        return Optional.ofNullable(resultNullable);
    }

}

package pl.edu.agh.iet.tsp.database.db.impl;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.edu.agh.iet.tsp.database.db.UserDao;
import pl.edu.agh.iet.tsp.database.domain.AuthenticationData;
import pl.edu.agh.iet.tsp.database.domain.User;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Wojciech Pachuta.
 */
@Repository
public class UserDaoImpl extends BasicDAO<User, UUID> implements UserDao {

    @Autowired
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

    @Override
    public Optional<User> findByAuthenticationData(AuthenticationData authenticationData) {
        User resultNullable = createQuery()
                .field(User.AUTHENTICATION_DATA + "." + AuthenticationData.AUTHENTICATION_PROVIDER).equal(authenticationData.getAuthenticationProvider())
                .field(User.AUTHENTICATION_DATA + "." + AuthenticationData.UNIQUE_ID).equal(authenticationData.getUniqueID())
                .get();
        return Optional.ofNullable(resultNullable);
    }

}

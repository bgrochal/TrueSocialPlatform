package pl.edu.agh.iet.tsp.core.db;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.edu.agh.iet.tsp.core.domain.User;

import java.util.UUID;

/**
 * @author Wojciech Pachuta.
 */
public interface UserRepository extends MongoRepository<User, UUID> {
    User findByUsername(String username);

    User findById(String username);
}

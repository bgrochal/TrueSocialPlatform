package pl.edu.agh.iet.tsp.core.config;

import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.edu.agh.iet.tsp.core.db.CommentDao;
import pl.edu.agh.iet.tsp.core.db.PostDao;
import pl.edu.agh.iet.tsp.core.db.UserDao;
import pl.edu.agh.iet.tsp.core.db.impl.CommentDaoImpl;
import pl.edu.agh.iet.tsp.core.db.impl.PostDaoImpl;
import pl.edu.agh.iet.tsp.core.db.impl.UserDaoImpl;

/**
 * @author Wojciech Pachuta.
 */
@Configuration
public class DatabaseConfig {
    @Bean
    public Morphia morphia() {
        Morphia morphia = new Morphia();
        morphia.mapPackage("pl.edu.agh.iet.tsp.core.domain");
        return morphia;
    }

    @Bean
    public MongoClient mongoClient() {
        return new MongoClient();
    }

    @Bean
    public UserDao userDao(Morphia morphia, MongoClient mongoClient){
        return new UserDaoImpl(ensureIndexes(morphia.createDatastore(mongoClient, "User")));
    }

    @Bean
    public PostDao postDao(Morphia morphia, MongoClient mongoClient){
        return new PostDaoImpl(ensureIndexes(morphia.createDatastore(mongoClient, "Post")));
    }

    @Bean
    public CommentDao commentDao(Morphia morphia, MongoClient mongoClient){
        return new CommentDaoImpl(ensureIndexes(morphia.createDatastore(mongoClient, "Comment")));
    }

    private static Datastore ensureIndexes(Datastore datastore){
        datastore.ensureIndexes();
        return datastore;
    }
}

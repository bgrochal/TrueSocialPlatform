package pl.edu.agh.iet.tsp.database.config;

import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Wojciech Pachuta.
 */
@Configuration
public class DatabaseConfig {
    @Bean
    public Morphia morphia() {
        Morphia morphia = new Morphia();
        morphia.mapPackage("pl.edu.agh.iet.tsp.database.domain");
        return morphia;
    }

    @Bean
    public MongoClient mongoClient() {
        return new MongoClient();
    }

    @Bean
    public Datastore db(Morphia morphia, MongoClient mongoClient) {
        return ensureIndexes(morphia.createDatastore(mongoClient, "TrueSocialPlatform"));
    }

    private static Datastore ensureIndexes(Datastore datastore){
        datastore.ensureIndexes();
        return datastore;
    }
}

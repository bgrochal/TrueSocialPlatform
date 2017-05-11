package pl.edu.agh.iet.tsp.core.routing.mapper;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.bson.types.ObjectId;

import java.io.IOException;

/**
 * @author Wojciech Pachuta.
 */
public class ObjectIdConverter extends SimpleModule {

    public ObjectIdConverter() {
        super("ObjectIdConverter");
        addSerializer(ObjectId.class, new JsonSerializer<ObjectId>() {
            @Override
            public void serialize(ObjectId objectId, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
                jsonGenerator.writeString(objectId.toString());
            }
        });
    }
}

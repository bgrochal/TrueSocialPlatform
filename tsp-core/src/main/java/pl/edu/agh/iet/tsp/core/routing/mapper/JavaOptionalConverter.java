package pl.edu.agh.iet.tsp.core.routing.mapper;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.util.Optional;

/**
 * @author Wojciech Pachuta.
 */
public class JavaOptionalConverter extends SimpleModule {

    public JavaOptionalConverter() {
        super("JavaOptionalConverter");
        addSerializer((Class<? extends Optional<Object>>)(Class<?>) Optional.class, new JsonSerializer<Optional<Object>>() {
            @Override
            public void serialize(Optional<Object> optional, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
                if (optional.isPresent()) {
                    jsonGenerator.writeObject(optional.get());
                } else {
                    jsonGenerator.writeNull();

                }

            }
        });
    }
}

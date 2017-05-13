package pl.edu.agh.iet.tsp.web.controller.mapper;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.time.LocalDateTime;

import static pl.edu.agh.iet.tsp.service.util.DateTimeUtils.utcMillis;

/**
 * @author Wojciech Pachuta.
 */
public class LocalDateTimeConverter extends SimpleModule {

    public LocalDateTimeConverter() {
        super("LocalDateTimeConverter");
        addSerializer(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
            @Override
            public void serialize(LocalDateTime dateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
                jsonGenerator.writeNumber(utcMillis(dateTime));
            }
        });
    }
}

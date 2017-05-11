package pl.edu.agh.iet.tsp.core.controller.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import pl.edu.agh.iet.tsp.core.controller.mapper.JavaOptionalConverter;
import pl.edu.agh.iet.tsp.core.controller.mapper.LocalDateTimeConverter;
import pl.edu.agh.iet.tsp.core.controller.mapper.ObjectIdConverter;

import java.util.Arrays;

/**
 * @author Wojciech Pachuta.
 */
@Configuration
public class MapperConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModules(Arrays.asList(
                new LocalDateTimeConverter(),
                new ObjectIdConverter(),
                new JavaOptionalConverter()
        ));
        return objectMapper;
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        jsonConverter.setObjectMapper(objectMapper());
        return jsonConverter;
    }

}

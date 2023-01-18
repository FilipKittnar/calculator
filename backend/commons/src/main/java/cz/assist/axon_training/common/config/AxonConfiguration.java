package cz.assist.axon_training.common.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.thoughtworks.xstream.XStream;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
import org.axonframework.serialization.xml.CompactDriver;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonConfiguration {

    private static final String[] WILDCARDS = {"cz.assist.**"};

    private static final JsonMapper MESSAGE_MAPPER = JsonMapper.builder()
        .visibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
        .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
        .serializationInclusion(JsonInclude.Include.NON_NULL)
        .addModules(new Jdk8Module())
        .build();

    @Bean
    public XStream axonXStream() {
        var xStream = new XStream(new CompactDriver());
        xStream.allowTypesByWildcard(WILDCARDS);
        return xStream;
    }

    @Bean
    @Qualifier("messageSerializer")
    public Serializer messageSerializer() {
        return JacksonSerializer.builder()
            .objectMapper(MESSAGE_MAPPER)
            .defaultTyping()
            .lenientDeserialization()
            .build();
    }

    @Bean
    @Qualifier("eventSerializer")
    public Serializer eventSerializer() {
        return JacksonSerializer.builder()
            .objectMapper(MESSAGE_MAPPER)
            .defaultTyping()
            .lenientDeserialization()
            .build();
    }
}

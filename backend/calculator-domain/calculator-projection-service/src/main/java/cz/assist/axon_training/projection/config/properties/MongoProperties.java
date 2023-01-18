package cz.assist.axon_training.projection.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("mongodb")
public class MongoProperties {

    private String uri;

    private String database;

}

package cz.assist.axon_training.projection.config;

import com.mongodb.client.MongoClient;
import org.axonframework.eventhandling.tokenstore.TokenStore;
import org.axonframework.extensions.mongo.DefaultMongoTemplate;
import org.axonframework.extensions.mongo.MongoTemplate;
import org.axonframework.extensions.mongo.eventsourcing.tokenstore.MongoTokenStore;
import org.axonframework.serialization.Serializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonCommonMongoConfiguration {

    @Bean
    public TokenStore tokenStore(Serializer serializer, MongoClient mongoClient) {
        return MongoTokenStore.builder()
            .mongoTemplate(axonMongoTemplate(mongoClient))
            .serializer(serializer)
            .build();
    }

    @Bean
    public MongoTemplate axonMongoTemplate(MongoClient client) {
        return DefaultMongoTemplate.builder()
            .mongoDatabase(client)
            .build();
    }

}

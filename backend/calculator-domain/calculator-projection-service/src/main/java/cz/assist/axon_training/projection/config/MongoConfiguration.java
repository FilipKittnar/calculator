package cz.assist.axon_training.projection.config;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import cz.assist.axon_training.projection.config.properties.MongoProperties;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Configuration
public class MongoConfiguration {

    @Bean
    public MongoClient mongoClient(MongoProperties mongoProperties) {
        return MongoClients.create(mongoProperties.getUri());
    }

    @Bean
    public MongoDatabase mongoDatabase(MongoClient mongoClient, MongoProperties mongoProperties) {
        var pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        var registries = CodecRegistries.fromRegistries(
            CodecRegistries.fromCodecs(new UuidCodec()), MongoClientSettings.getDefaultCodecRegistry());
        var pojoCodecRegistry = fromRegistries(registries, fromProviders(pojoCodecProvider));
        return mongoClient.getDatabase(mongoProperties.getDatabase())
            .withCodecRegistry(pojoCodecRegistry);
    }

}

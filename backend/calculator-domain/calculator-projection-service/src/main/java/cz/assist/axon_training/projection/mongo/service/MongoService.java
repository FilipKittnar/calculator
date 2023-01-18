package cz.assist.axon_training.projection.mongo.service;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import cz.assist.axon_training.projection.domain.history.entity.CalculatorEntity;
import cz.assist.axon_training.projection.mongo.entity.MongoEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class MongoService<T extends CalculatorEntity> {

    private final MongoCollection<T> collection;

    protected void insertOne(T entity) {
        log.debug("Going to insert entity {} into collection {}.", entity, collection.getNamespace().getCollectionName());

        if (entity.getId() == null) {
            entity.setId(new ObjectId());
        }
        collection.insertOne(entity);

        log.info("Inserted entity {}.", entity);
        log.debug("Successfully inserted entity {} into collection {}.", entity, collection.getNamespace().getCollectionName());
    }

    public T findFirst(Bson query) {
        log.debug("Going to find first mongo entity in collection {} using query {}.", collection.getNamespace().getFullName(), query);

        var entity = collection.find(query).first();

        log.debug("Found entity {}", entity);
        return entity;
    }

    public T findFirst(Bson query, Bson sort) {
        log.debug("Going to find first mongo entity in collection {} using query {} and sort {}.",
            collection.getNamespace().getFullName(), query, sort);

        var entity = collection.find(query).sort(sort).first();

        log.debug("Found entity {}", entity);
        return entity;
    }

    public List<T> find(Bson query, int limit, Bson sort) {
        return this.find(query, limit, Optional.ofNullable(sort));
    }

    public List<T> find(Bson query, int limit, Optional<Bson> sort) {
        var sortWithId = Sorts.orderBy(sort.map(s -> {
            if (!s.toBsonDocument().containsKey(MongoEntity.ID_FIELD_NAME)) {
                return List.of(s, Sorts.ascending(MongoEntity.ID_FIELD_NAME));
            }
            return Collections.singletonList(s);
        }).orElse(List.of(Sorts.ascending(MongoEntity.ID_FIELD_NAME))));

        log.debug("Going to find mongo entities in collection {} using query {}, sort {} and limit {}.",
            collection.getNamespace().getFullName(), query, sortWithId, limit);
        var iterator = collection.find(query).limit(limit).sort(sortWithId).spliterator();

        var entities = StreamSupport.stream(iterator, false).toList();

        log.debug("Found entities {}", entities);
        return entities;
    }


    protected void updateOne(String idFieldName, String id, List<Bson> updates) {
        log.debug("Going to apply updates {} with id {} in collection {}.", updates, id, collection.getNamespace().getFullName());
        collection.updateOne(Filters.eq(idFieldName, id), Updates.combine(updates));

        log.debug("Successfully updated mongo entity {} in collection {}.", id, collection.getNamespace().getFullName());
    }

}

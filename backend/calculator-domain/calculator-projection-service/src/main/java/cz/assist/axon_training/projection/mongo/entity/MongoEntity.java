package cz.assist.axon_training.projection.mongo.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@SuperBuilder
public abstract class MongoEntity {

    public static final String ID_FIELD_NAME = "_id";

    @BsonId
    private ObjectId id;

}

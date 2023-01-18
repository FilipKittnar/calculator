package cz.assist.axon_training.projection.config;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.springframework.util.StringUtils;

import java.util.UUID;

public class UuidCodec implements Codec<UUID> {

    @Override
    public void encode(BsonWriter writer, UUID value, EncoderContext encoderContext) {
        if (value != null) {
            writer.writeString(value.toString());
        }
    }

    @Override
    public UUID decode(BsonReader reader, DecoderContext decoderContext) {
        var uuidString = reader.readString();
        if (!StringUtils.hasText(uuidString)) {
            return null;
        }

        return UUID.fromString(uuidString);
    }

    @Override
    public Class<UUID> getEncoderClass() {
        return UUID.class;
    }
}

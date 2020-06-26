package com.dac.chatting.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;

public interface JsonSupport {

    static ObjectMapper jsonMapper() {
        return new ObjectMapper()
            .registerModule(new CustomJodaModule());
    }
}

class CustomJodaModule extends SimpleModule {

    CustomJodaModule() {
        super();
        final DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS Z");
        final DateTimeFormatter formatLDT = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS");
        addSerializer(DateTime.class, new DateTimeSerializer(format));
        addDeserializer(DateTime.class, new DateTimeDeserializer(format));
        addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(formatLDT));
        addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(formatLDT));
    }
}

class LocalDateTimeSerializer extends StdScalarSerializer<LocalDateTime> {

    final private DateTimeFormatter format;

    LocalDateTimeSerializer(final DateTimeFormatter format) {
        super(LocalDateTime.class);
        this.format = format;
    }

    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(format.print(value));
    }
}

class LocalDateTimeDeserializer extends StdScalarDeserializer<LocalDateTime> {

    final private DateTimeFormatter format;

    LocalDateTimeDeserializer(final DateTimeFormatter format) {
        super(LocalDateTime.class);
        this.format = format;
    }

    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext dContext) throws IOException {
        return format.parseLocalDateTime(jsonParser.getText());
    }
}

class DateTimeSerializer extends StdScalarSerializer<DateTime> {

    final private DateTimeFormatter format;

    DateTimeSerializer(final DateTimeFormatter format) {
        super(DateTime.class);
        this.format = format;
    }

    @Override
    public void serialize(DateTime value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(format.print(value));
    }
}

class DateTimeDeserializer extends StdScalarDeserializer<DateTime> {

    final private DateTimeFormatter format;

    DateTimeDeserializer(final DateTimeFormatter format) {
        super(DateTime.class);
        this.format = format;
    }

    @Override
    public DateTime deserialize(JsonParser jsonParser, DeserializationContext dContext) throws IOException {
        return format.parseDateTime(jsonParser.getText());
    }
}

/* * * * * Copyright (c) 2025 David Wrenner * * * * */
package io.github.davidwrenner.trains.pojo;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;

public class LineCodeDeserializer extends StdDeserializer<LineCode> {

    public LineCodeDeserializer() {
        super(LineCode.class);
    }

    @Override
    public LineCode deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        String value = jsonParser.getText();
        if (value == null) {
            return LineCode.NULL;
        }
        return LineCode.valueOf(value.toUpperCase());
    }

    @Override
    public LineCode getNullValue(DeserializationContext ctxt) {
        return LineCode.NULL;
    }
}

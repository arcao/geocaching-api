package com.arcao.geocaching.api.impl.live_geocaching_api.builder;

import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * This interface must implement all classes which can be serializable to JSON.
 *
 * @author arcao
 */
public interface JsonSerializable {
    /**
     * Called during serialization process and must be implemented.
     *
     * @param w JsonWriter object for writing output
     * @throws IOException If I/O error occurs
     */
    void writeJson(JsonWriter w) throws IOException;
}

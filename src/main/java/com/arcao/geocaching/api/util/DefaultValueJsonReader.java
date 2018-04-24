package com.arcao.geocaching.api.util;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.Reader;

/**
 * JSON Reader implementation which supports JSON null values in nextXXX method calls. In these cases
 * return default values.
 */
public class DefaultValueJsonReader extends JsonReader {
    /**
     * Creates a new instance that reads a JSON-encoded stream from {@code in}.
     */
    public DefaultValueJsonReader(Reader in) {
        super(in);
    }

    /**
     * Returns the {@link JsonToken#STRING string} value of the next token,
     * consuming it. If the next token is a number, this method will return its
     * string form.
     *
     * @throws IllegalStateException if the next token is not a string or if
     *                               this reader is closed.
     * @throws IOException           when I/O error occurs
     */
    @Override
    @Nullable
    public String nextString() throws IOException {
        return nextString(null);
    }

    public String nextString(String defaultValue) throws IOException {
        if (peek() == JsonToken.NULL) {
            skipValue();
            return defaultValue;
        }

        return super.nextString();
    }

    /**
     * Returns the {@link JsonToken#BOOLEAN boolean} value of the next token,
     * consuming it.
     *
     * @throws IllegalStateException if the next token is not a boolean or if
     *                               this reader is closed.
     * @throws IOException           when I/O error occurs
     */
    @Override
    public boolean nextBoolean() throws IOException {
        return nextBoolean(false);
    }

    public boolean nextBoolean(boolean defaultValue) throws IOException {
        if (peek() == JsonToken.NULL) {
            skipValue();
            return defaultValue;
        }

        return super.nextBoolean();
    }

    /**
     * Returns the {@link JsonToken#NUMBER double} value of the next token,
     * consuming it. If the next token is a string, this method will attempt to
     * parse it as a double.
     *
     * @throws IllegalStateException if the next token is not a literal value.
     * @throws NumberFormatException if the next literal value cannot be parsed
     *                               as a double, or is non-finite.
     * @throws IOException           when I/O error occurs
     */
    @Override
    public double nextDouble() throws IOException {
        return nextDouble(0D);
    }

    public double nextDouble(double defaultValue) throws IOException {
        if (peek() == JsonToken.NULL) {
            skipValue();
            return defaultValue;
        }

        return super.nextDouble();
    }

    /**
     * Returns the {@link JsonToken#NUMBER long} value of the next token,
     * consuming it. If the next token is a string, this method will attempt to
     * parse it as a long. If the next token's numeric value cannot be exactly
     * represented by a Java {@code long}, this method throws.
     *
     * @throws IllegalStateException if the next token is not a literal value.
     * @throws NumberFormatException if the next literal value cannot be parsed
     *                               as a number, or exactly represented as a long.
     * @throws IOException           when I/O error occurs
     */
    @Override
    public long nextLong() throws IOException {
        return nextLong(0L);
    }

    public long nextLong(long defaultValue) throws IOException {
        if (peek() == JsonToken.NULL) {
            skipValue();
            return defaultValue;
        }

        return super.nextLong();
    }

    /**
     * Returns the {@link JsonToken#NUMBER int} value of the next token,
     * consuming it. If the next token is a string, this method will attempt to
     * parse it as an int. If the next token's numeric value cannot be exactly
     * represented by a Java {@code int}, this method throws.
     *
     * @throws IllegalStateException if the next token is not a literal value.
     * @throws NumberFormatException if the next literal value cannot be parsed
     *                               as a number, or exactly represented as an int.
     * @throws IOException           when I/O error occurs
     */
    @Override
    public int nextInt() throws IOException {
        return nextInt(0);
    }

    public int nextInt(int defaultValue) throws IOException {
        if (peek() == JsonToken.NULL) {
            skipValue();
            return defaultValue;
        }

        return super.nextInt();
    }
}

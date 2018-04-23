package com.arcao.geocaching.api.data;


import com.arcao.geocaching.api.data.type.GeocacheLogType;
import com.google.auto.value.AutoValue;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@AutoValue
public abstract class FieldNote implements Serializable {
    private static final long serialVersionUID = 249144828657285091L;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    static {
        DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    public static FieldNote parseLine(String line) {
        String[] items = line.split(",", 4);

        String note = items[3];
        if (note.length() >= 2 && note.charAt(0) == '\"' && note.charAt(note.length() - 1) == '\"') {
            note = note.substring(1, note.length() - 1);
        }

        try {
            return builder()
                    .cacheCode(items[0])
                    .dateLogged(DATE_FORMAT.parse(items[1]))
                    .logType(GeocacheLogType.fromName(items[2]))
                    .note(note)
                    .build();
        } catch (ParseException e) {
            return null;
        }
    }

    private static String safeNote(String note) {
        return note.replace('"', '\'').replaceAll("[\r\n\t]+", "");
    }

    public static Builder builder() {
        return new AutoValue_FieldNote.Builder();
    }

    public abstract String cacheCode();

    public abstract Date dateLogged();

    public abstract GeocacheLogType logType();

    public abstract String note();

    @Override
    public String toString() {
        return String.format(
                "%s,%s,%s,\"%s\"", cacheCode(),
                DATE_FORMAT.format(dateLogged()),
                logType().name,
                safeNote(note())
        );
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder cacheCode(String cacheCode);

        public abstract Builder dateLogged(Date dateLogged);

        public abstract Builder logType(GeocacheLogType logType);

        public abstract Builder note(String note);

        public abstract FieldNote build();
    }
}

package com.arcao.geocaching.api.data;

import com.arcao.geocaching.api.builder.JsonSerializable;
import com.arcao.geocaching.api.util.Base64;
import com.arcao.geocaching.api.util.Base64OutputStream;
import com.google.auto.value.AutoValue;
import com.google.gson.stream.JsonWriter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;

@AutoValue
public abstract class ImageData implements JsonSerializable, Serializable {
    private static final long serialVersionUID = 1116404414881607691L;
    private static final int BUFFER_SIZE = 32768;

    @NotNull
    public abstract String name();

    @Nullable
    public abstract String description();

    @Nullable
    public abstract String mobileUrl();

    @Nullable
    public abstract String thumbUrl();

    @Nullable
    public abstract String url();

    @Nullable
    public abstract String fileName();

    @Nullable
    public abstract Date created();

    @Nullable
    @SuppressWarnings("mutable")
    public abstract byte[] imageData();

    public static ImageData createFromInputStream(@NotNull String name, @Nullable String description,
                                                  String fileName, InputStream is) throws IOException {
        byte[] buffer = new byte[BUFFER_SIZE];

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Base64OutputStream b64os = new Base64OutputStream(bos, Base64.NO_CLOSE | Base64.NO_WRAP);

        try {
            while (true) {
                int bytesRead = is.read(buffer);
                if (bytesRead < 0) {
                    break;
                }

                b64os.write(buffer, 0, bytesRead);
            }
        } finally {
            b64os.flush();
            b64os.close();

            is.close();
        }

        return builder()
                .name(name)
                .description(description)
                .fileName(fileName)
                .imageData(bos.toByteArray())
                .build();
    }

    @Override
    public void writeJson(@NotNull JsonWriter w) throws IOException {
        byte[] imageData = imageData();

        if (imageData == null) {
            return;
        }

        w.beginObject()
                .name("FileCaption").value(name())
                .name("FileDescription").value(description())
                .name("FileName").value(fileName())
                .name("base64ImageData").value(new String(imageData));
        w.endObject();
    }

    public static Builder builder() {
        return new AutoValue_ImageData.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder name(String name);

        public abstract Builder description(String description);

        public abstract Builder mobileUrl(String mobileUrl);

        public abstract Builder thumbUrl(String thumbUrl);

        public abstract Builder url(String url);

        public abstract Builder fileName(String fileName);

        public abstract Builder imageData(byte[] imageData);

        public abstract Builder created(Date created);

        public abstract ImageData build();
    }
}

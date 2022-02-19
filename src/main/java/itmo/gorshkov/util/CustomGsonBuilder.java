package itmo.gorshkov.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import itmo.gorshkov.entity.MusicGenre;

import javax.validation.ValidationException;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class CustomGsonBuilder {
    public static Gson create() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter().nullSafe())
                .registerTypeAdapter(BigDecimal.class, new BigDecimalAdapter().nullSafe())
                .registerTypeAdapter(Integer.class, new IntegerAdapter().nullSafe())
                .registerTypeAdapter(Long.class, new LongAdapter().nullSafe())
                .registerTypeAdapter(MusicGenre.class, new MusicGenreAdapter().nullSafe())
                .create();
    }

    private static final class LocalDateAdapter extends TypeAdapter<LocalDate> {
        @Override
        public void write(final JsonWriter jsonWriter, final LocalDate localDate) throws IOException {
            jsonWriter.value(localDate.toString());
        }

        @Override
        public LocalDate read(final JsonReader jsonReader) throws IOException {
                String token = jsonReader.nextString();
                if (token.isEmpty()) {
                    return null;
                }
                return LocalDate.parse(token);
        }
    }

    private static final class BigDecimalAdapter extends TypeAdapter<BigDecimal> {
        @Override
        public void write(final JsonWriter jsonWriter, final BigDecimal bigDecimal) throws IOException {
            jsonWriter.value(bigDecimal);
        }

        @Override
        public BigDecimal read(final JsonReader jsonReader) throws IOException {
            try {
                String token = jsonReader.nextString();
                if (token.isEmpty()) {
                    return null;
                }
                return new BigDecimal(token);
            } catch (NumberFormatException e) {
                String message = "field " + jsonReader.getPath().substring(2) + " must be float";
                throw new ValidationException(message, e);
            }
        }
    }

    private static final class IntegerAdapter extends TypeAdapter<Integer> {
        @Override
        public void write(final JsonWriter jsonWriter, final Integer integer) throws IOException {
            jsonWriter.value(integer);
        }

        @Override
        public Integer read(final JsonReader jsonReader) throws IOException {
            try {
                String token = jsonReader.nextString();
                if (token.isEmpty()) {
                    return null;
                }
                return Integer.parseInt(token);
            } catch (NumberFormatException e) {
                String message = "field " + jsonReader.getPath().substring(2) + " must be integer";
                throw new ValidationException(message, e);
            }
        }
    }

    private static final class LongAdapter extends TypeAdapter<Long> {
        @Override
        public void write(final JsonWriter jsonWriter, final Long number) throws IOException {
            jsonWriter.value(number);
        }

        @Override
        public Long read(final JsonReader jsonReader) throws IOException {
            try {
                String token = jsonReader.nextString();
                if (token.isEmpty()) {
                    return null;
                }
                return Long.parseLong(token);
            } catch (NumberFormatException e) {
                String message = "field " + jsonReader.getPath().substring(2) + " must be long";
                throw new ValidationException(message, e);
            }
        }
    }

    private static final class MusicGenreAdapter extends TypeAdapter<MusicGenre> {
        @Override
        public void write(final JsonWriter jsonWriter, final MusicGenre genre) throws IOException {
            jsonWriter.value(genre.name());
        }

        @Override
        public MusicGenre read(final JsonReader jsonReader) throws IOException {
            try {
                String token = jsonReader.nextString();
                if (token.isEmpty()) {
                    return null;
                }
                return MusicGenre.valueOf(token);
            } catch (IllegalArgumentException e) {
                String message = "field " + jsonReader.getPath().substring(2) + " must be one of 'ROCK, SOUL, BLUES, POP, POST_PUNK'";
                throw new ValidationException(message, e);
            }
        }
    }
}

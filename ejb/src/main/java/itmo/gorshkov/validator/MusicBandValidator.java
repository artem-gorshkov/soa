package itmo.gorshkov.validator;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import itmo.gorshkov.entity.MusicBand;
import itmo.gorshkov.util.CustomGsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.format.DateTimeParseException;
import java.util.Set;

public class MusicBandValidator {

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    Gson gson = CustomGsonBuilder.create();

    public static Logger logger = LoggerFactory.getLogger(MusicBandValidator.class);

    public MusicBand validate(String json) {
        if (json == null) {
            throw new RuntimeException("Request body must be specified");
        }

        MusicBand musicBand = parse(json);

        Set<ConstraintViolation<MusicBand>> violations = validator.validate(musicBand);
        if (violations.isEmpty()) {
            return musicBand;
        }

        StringBuilder errorMessage = new StringBuilder();
        for (ConstraintViolation<MusicBand> violation : violations) {
            logger.error(violation.getMessage());
            errorMessage.append(violation.getMessage()).append(" \n ");
        }

        throw new RuntimeException(errorMessage.toString());
    }

    private MusicBand parse(String jsonString){
        MusicBand musicBand = null;
        try {
            if (jsonString.isEmpty()) {
                throw new RuntimeException("Empty string, model expected");
            }

            musicBand = gson.fromJson(jsonString, MusicBand.class);
        } catch (JsonSyntaxException e) {
            throw new RuntimeException("Invalid JSON in request body");
        } catch (DateTimeParseException e) {
            throw new RuntimeException("Creation Date should be 'YYYY-MM-DD'");
        } catch (ValidationException e) {
            throw new RuntimeException(e.getMessage());
        }
        return musicBand;
    }
}

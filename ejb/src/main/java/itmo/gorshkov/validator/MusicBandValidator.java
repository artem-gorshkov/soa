package itmo.gorshkov.validator;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import itmo.gorshkov.entity.MusicBand;
import itmo.gorshkov.util.CustomGsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;
import java.time.format.DateTimeParseException;
import java.util.Set;

import static itmo.gorshkov.util.ResponseUtil.errorResponse;

public class MusicBandValidator {

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    Gson gson = CustomGsonBuilder.create();

    public static Logger logger = LoggerFactory.getLogger(MusicBandValidator.class);

    public MusicBand validate(String json) {
        if (json == null) {
            throw new BadRequestException(Response.status(HttpServletResponse.SC_BAD_REQUEST).entity("Request body must be specified").build());
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

        throw new BadRequestException(errorResponse(errorMessage.toString()));
    }

    private MusicBand parse(String jsonString){
        MusicBand musicBand = null;
        try {
            if (jsonString.isEmpty()) {
                throw new BadRequestException(errorResponse("Empty string, model expected"));
            }

            musicBand = gson.fromJson(jsonString, MusicBand.class);
        } catch (JsonSyntaxException e) {
            throw new BadRequestException(errorResponse("Invalid JSON in request body"));
        } catch (DateTimeParseException e) {
            throw new BadRequestException(errorResponse("Creation Date should be 'YYYY-MM-DD'"));
        } catch (ValidationException e) {
            throw new BadRequestException(errorResponse(e.getMessage()));
        }
        return musicBand;
    }
}

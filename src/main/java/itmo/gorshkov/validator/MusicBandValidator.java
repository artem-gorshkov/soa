package itmo.gorshkov.validator;

import itmo.gorshkov.entity.MusicBand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;
import java.util.Set;

import static itmo.gorshkov.util.ResponseUtil.errorResponse;

public class MusicBandValidator {

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    public static Logger logger = LoggerFactory.getLogger(MusicBandValidator.class);

    public void validate(MusicBand musicBand) {
        if (musicBand == null) {
            throw new BadRequestException(Response.status(HttpServletResponse.SC_BAD_REQUEST).entity("Request body must be specified").build());
        }

        Set<ConstraintViolation<MusicBand>> violations = validator.validate(musicBand);
        if (violations.isEmpty()) {
            return;
        }

        StringBuilder errorMessage = new StringBuilder();
        for (ConstraintViolation<MusicBand> violation : violations) {
            logger.error(violation.getMessage());
            errorMessage.append(violation.getMessage()).append(" \n ");
        }

        throw new BadRequestException(errorResponse(errorMessage.toString()));
    }
}

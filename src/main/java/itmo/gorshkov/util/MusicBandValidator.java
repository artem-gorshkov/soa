package itmo.gorshkov.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import itmo.gorshkov.entity.MusicBand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Set;
import java.util.stream.Collectors;

import static itmo.gorshkov.util.WriteErrorUtil.writeError;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;

public class MusicBandValidator {

    Gson gson = CustomGsonBuilder.create();
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    public static Logger logger = LoggerFactory.getLogger(MusicBandValidator.class);

    public boolean validate(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        MusicBand musicBand = parse(req, resp);
        if (musicBand == null) {
            return false;
        }

        processCreationDate(musicBand);

        if (validIdInUpdateRequest(req, resp, musicBand)) {
            return false;
        }

        Set<ConstraintViolation<MusicBand>> violations = validator.validate(musicBand);
        if (violations.isEmpty()) {
            req.setAttribute("band", musicBand);
            return true;
        }
        
        StringBuilder errorMessage = new StringBuilder();
        for (ConstraintViolation<MusicBand> violation : violations) {
            logger.error(violation.getMessage());
            errorMessage.append(violation.getMessage()).append(" \n ");
        }

        writeError(resp, SC_BAD_REQUEST, errorMessage.toString());
        
        return false;
    }

    private boolean validIdInUpdateRequest(HttpServletRequest req, HttpServletResponse resp, MusicBand musicBand) throws IOException {
        if (req.getMethod().equals("PUT") && musicBand.getId() == null) {
            writeError(resp, SC_BAD_REQUEST, "id must present in request body");
            return true;
        }
        return false;
    }

    private void processCreationDate(MusicBand musicBand) {
        if (musicBand.getCreationDate() == null) {
            musicBand.setCreationDate(LocalDate.now());
        }
    }

    private MusicBand parse(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        MusicBand musicBand = null;
        try {
            String jsonString = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            musicBand = gson.fromJson(jsonString, MusicBand.class);
        } catch (JsonSyntaxException e) {
            writeError(resp, SC_BAD_REQUEST, "Invalid JSON in request body");
        } catch (DateTimeParseException e) {
            writeError(resp, SC_BAD_REQUEST, "Creation Date should be 'YYYY-MM-DD'");
        } catch (ValidationException e) {
            writeError(resp, SC_BAD_REQUEST, e.getMessage());
        }
        return musicBand;
    }
}

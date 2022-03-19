package itmo.gorshkov.validator;

import itmo.gorshkov.config.FilterConfiguration;
import itmo.gorshkov.entity.MusicBand;
import itmo.gorshkov.entity.MusicGenre;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static itmo.gorshkov.entity.MusicBand.getMusicBandFieldNames;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;

public class OptionValidator {
    private static final List<String> EXPECTED_FIELDS = getMusicBandFieldNames();
    private static final List<String> EXPECTED_ACTIONS = Collections.unmodifiableList(Arrays.asList("<", ">", "==", "<=", ">=", "contains"));

    public void validate(FilterConfiguration filterConfiguration) {
            processCount(filterConfiguration);
            processOrder(filterConfiguration);
            processFilter(filterConfiguration);
    }

    private void processCount(FilterConfiguration filterConfiguration) {
        if (filterConfiguration.getCount() != null && !(filterConfiguration.getCount() > 0)) {
            throw new RuntimeException("Parameter 'count' must be bigger than 0");
        }

        if (filterConfiguration.getPage() != null && !(filterConfiguration.getPage() > 0)) {
            throw new RuntimeException("Parameter 'page' must be bigger than 0");
        }
    }

    private void processOrder(FilterConfiguration filterConfiguration) {
        if (filterConfiguration.getOrder() != null) {
            for (String order : filterConfiguration.getOrder()) {
                String[] parts = order.split(",");
                if (parts.length == 2) {
                    if (!EXPECTED_FIELDS.contains(parts[0])) {
                        throw new RuntimeException("Unexpected field '" + parts[0] + "' specified to order by '" + order + "'. Check documentation for details.");
                    }
                    if (!parts[1].equals("d") && !parts[1].equals("a")) {
                        throw new RuntimeException("Order direction must be 'a' (asc) or 'd' (desc).");
                    }
                } else {
                    throw new RuntimeException("Order parameter has invalid format '" + order + "'. Check documentation for details.");
                }
            }
        }
    }

    private void processFilter(FilterConfiguration filterConfiguration) {
        if (filterConfiguration.getFilter() != null) {
            for (String filter : filterConfiguration.getFilter()) {
                String[] parts = filter.split(",");

                if (!EXPECTED_FIELDS.contains(parts[0])) {
                    throw new RuntimeException("Unexpected field '" + parts[0] + "' specified in filter '" + filter + "'. Check documentation for details.");
                }

                if (!EXPECTED_ACTIONS.contains(parts[1])) {
                    throw new RuntimeException("Unexpected action '" + parts[0] + "' specified in filter '" + filter + "'. Check documentation for details.");
                }

                if (parts.length != 3) {
                    throw new RuntimeException("Filter parameter has invalid format '" + filter + "'. Check documentation for details.");
                }

                processFieldsInFilters(parts);
            }
        }
    }

    private void processFieldsInFilters(String[] parts) {
        for (Field field : MusicBand.class.getDeclaredFields()) {
            if (field.getName().equals(parts[0])) {
                if (Integer.class.equals(field.getType())) {
                    try {
                        parseInt(parts[2]);
                    } catch (NumberFormatException e) {
                        throw new RuntimeException("Field '" + field.getName() + "' in filter must be integer");
                    }
                } else if (BigDecimal.class.equals(field.getType())) {
                    try {
                        new BigDecimal(parts[2]);
                    } catch (NumberFormatException e) {
                        throw new RuntimeException("Field '" + field.getName() + "' in filter must be float");
                    }
                } else if (Long.class.equals(field.getType())) {
                    try {
                        parseLong(parts[2]);
                    } catch (NumberFormatException e) {
                        throw new RuntimeException("Field '" + field.getName() + "' in filter must be long");
                    }
                } else if (MusicGenre.class.equals(field.getType())) {
                    try {
                        MusicGenre.valueOf(parts[2]);
                    } catch (IllegalArgumentException e) {
                        throw new RuntimeException("Field '" + field.getName() + "' in filter must be one of expected value. Check documentation for details");
                    }
                } else if (LocalDate.class.equals(field.getType())) {
                    try {
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        formatter.parse(parts[2]);
                    } catch (ParseException e) {
                        throw new RuntimeException("Field '" + field.getName() + "' in filter must have format yyyy-MM-dd");
                    }
                }
            }
        }
    }
}

package itmo.gorshkov.filter;

import itmo.gorshkov.entity.MusicBand;
import itmo.gorshkov.entity.MusicGenre;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static itmo.gorshkov.entity.MusicBand.getMusicBandFieldNames;
import static itmo.gorshkov.util.WriteErrorUtil.writeError;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;

@WebFilter("/api/band/*")
public class OptionsFilter implements Filter {
    private static final List<String> EXPECTED_FIELDS = getMusicBandFieldNames();
    private static final List<String> EXPECTED_ACTIONS = Collections.unmodifiableList(Arrays.asList("<", ">", "==", "<=", ">=", "contains"));

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        if (req.getMethod().equals("GET")) {
            if (processCount(req, resp)) {
                return;
            }

            if (processOrder(req, resp)) {
                return;
            }

            if (processFilter(req, resp)) {
                return;
            }
        }

        filterChain.doFilter(req, resp);
    }

    private boolean processFilter(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getParameter("filter") == null) {
            return false;
        }

        for (String filter : req.getParameterValues("filter")) {
            String[] parts = filter.split(",");

            if (!EXPECTED_FIELDS.contains(parts[0])) {
                writeError(resp, HttpServletResponse.SC_BAD_REQUEST, "Unexpected field '" + parts[0] + "' specified in filter '" + filter + "'. Check documentation for details.");
                return true;
            }

            if (!EXPECTED_ACTIONS.contains(parts[1])) {
                writeError(resp, HttpServletResponse.SC_BAD_REQUEST, "Unexpected action '" + parts[0] + "' specified in filter '" + filter + "'. Check documentation for details.");
                return true;
            }

            if (parts.length != 3) {
                writeError(resp, HttpServletResponse.SC_BAD_REQUEST, "Filter parameter has invalid format '" + filter + "'. Check documentation for details.");
                return true;
            }

            if (processFieldsInFilters(resp, parts)) return true;
        }
        return false;
    }

    private boolean processFieldsInFilters(HttpServletResponse resp, String[] parts) throws IOException {
        for (Field field : MusicBand.class.getDeclaredFields()) {
            if (field.getName().equals(parts[0])) {
                if (Integer.class.equals(field.getType())) {
                    try {
                        parseInt(parts[2]);
                    } catch (NumberFormatException e) {
                        writeError(resp, HttpServletResponse.SC_BAD_REQUEST, "Field '" + field.getName() + "' in filter must be integer");
                        return true;
                    }
                } else if (BigDecimal.class.equals(field.getType())) {
                    try {
                        new BigDecimal(parts[2]);
                    } catch (NumberFormatException e) {
                        writeError(resp, HttpServletResponse.SC_BAD_REQUEST, "Field '" + field.getName() + "' in filter must be float");
                        return true;
                    }
                } else if (Long.class.equals(field.getType())) {
                    try {
                        parseLong(parts[2]);
                    } catch (NumberFormatException e) {
                        writeError(resp, HttpServletResponse.SC_BAD_REQUEST, "Field '" + field.getName() + "' in filter must be long");
                        return true;
                    }
                } else if (MusicGenre.class.equals(field.getType())) {
                    try {
                        MusicGenre.valueOf(parts[2]);
                    } catch (IllegalArgumentException e) {
                        writeError(resp, HttpServletResponse.SC_BAD_REQUEST, "Field '" + field.getName() + "' in filter must be one of expected value. Check documentation for details");
                        return true;
                    }
                } else if (LocalDate.class.equals(field.getType())) {
                    try {
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        formatter.parse(parts[2]);
                    } catch (ParseException e) {
                        writeError(resp, HttpServletResponse.SC_BAD_REQUEST, "Field '" + field.getName() + "' in filter must have format yyyy-MM-dd");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean processOrder(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getParameter("order") != null) {
            for (String order : req.getParameterValues("order")) {
                String[] parts = order.split(",");
                if (parts.length == 2) {
                    if (!EXPECTED_FIELDS.contains(parts[0])) {
                        writeError(resp, HttpServletResponse.SC_BAD_REQUEST, "Unexpected field '" + parts[0] + "' specified to order by '" + order + "'. Check documentation for details.");
                        return true;
                    }
                    if (!parts[1].equals("d") && !parts[1].equals("a")) {
                        writeError(resp, HttpServletResponse.SC_BAD_REQUEST, "Order direction must be 'a' (asc) or 'd' (desc).");
                        return true;
                    }
                } else {
                    writeError(resp, HttpServletResponse.SC_BAD_REQUEST, "Order parameter has invalid format '" + order + "'. Check documentation for details.");
                    return true;
                }
            }
        }
        return false;
    }

    private boolean processCount(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getParameter("count") != null) {
            int count, page;

            try {
                count = parseInt(req.getParameter("count"));

                if (!(count > 0)) {
                    writeError(resp, HttpServletResponse.SC_BAD_REQUEST, "Parameter 'count' must be bigger than 0");
                    return true;
                }

                req.setAttribute("count", count);
            } catch (NumberFormatException e) {
                writeError(resp, HttpServletResponse.SC_BAD_REQUEST, "Parameter 'count' must be integer");
                return true;
            }

            if (req.getParameter("page") != null) {
                try {
                    page = parseInt(req.getParameter("page"));

                    if (!(page > 0)) {
                        writeError(resp, HttpServletResponse.SC_BAD_REQUEST, "Parameter 'page' must be bigger than 0");
                        return true;
                    }

                    req.setAttribute("page", page);
                } catch (NumberFormatException e) {
                    writeError(resp, HttpServletResponse.SC_BAD_REQUEST, "Parameter 'page' must be integer");
                    return true;
                }
            } else {
                req.setAttribute("page", 1);
            }
        }
        return false;
    }

    @Override
    public void destroy() {

    }
}

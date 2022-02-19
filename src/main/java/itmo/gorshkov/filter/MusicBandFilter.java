package itmo.gorshkov.filter;

import itmo.gorshkov.util.MusicBandValidator;

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

import static itmo.gorshkov.util.WriteErrorUtil.writeError;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;

@WebFilter("/api/band/*")
public class MusicBandFilter implements Filter {
    private MusicBandValidator musicBandValidator;

    public static final String MISSING_ID = "Id must be specified in the end of request";
    public static final String ID_NOT_INTEGER = "Field 'id' must be integer";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.musicBandValidator = new MusicBandValidator();
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String pathInfo = req.getPathInfo() != null ? req.getPathInfo() : "";
        String rawPathInfo = pathInfo.replaceAll("^/", "");

        switch (req.getMethod()) {
            case "DELETE":
                if (rawPathInfo.isEmpty()) {
                    writeError(resp, SC_BAD_REQUEST, MISSING_ID);
                    return;
                }
            case "GET":
                if (!validateId(req, resp, rawPathInfo)) {
                    return;
                }
                break;
            case "POST":
            case "PUT":
                if (!musicBandValidator.validate(req, resp)) {
                    return;
                }
                break;
        }

        filterChain.doFilter(req, resp);
    }

    private Boolean validateId(HttpServletRequest req, HttpServletResponse resp, String rawPathInfo) throws IOException {
        if (!rawPathInfo.isEmpty() && !rawPathInfo.equals("count_by/number_of_participants")) {
            try {
                Integer id = Integer.valueOf(rawPathInfo);
                req.setAttribute("id", id);
            } catch (NumberFormatException e) {
                writeError(resp, SC_BAD_REQUEST, ID_NOT_INTEGER);
                return false;
            }
        }
        return true;
    }

    @Override
    public void destroy() {

    }
}

package itmo.gorshkov.util;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

public class ResponseUtil {

    public static Response errorResponse(String message) {
        return Response.status(HttpServletResponse.SC_BAD_REQUEST).entity(message).build();
    }
}

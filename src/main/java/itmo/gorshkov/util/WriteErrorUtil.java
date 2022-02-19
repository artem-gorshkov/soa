package itmo.gorshkov.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WriteErrorUtil {

    public static void writeError(HttpServletResponse resp, int returnCode, String errorMessage) throws IOException {
        Gson gson = (new GsonBuilder()).create();

        resp.setContentType("application/json");
        resp.setStatus(returnCode);
        resp.getWriter().write(gson.toJson(new Error(errorMessage)));
    }

}

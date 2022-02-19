package itmo.gorshkov.controller;

import com.google.gson.Gson;
import itmo.gorshkov.config.FilterConfiguration;
import itmo.gorshkov.entity.MusicBand;
import itmo.gorshkov.repository.MusicBandRepositoryImpl;
import itmo.gorshkov.service.MusicBandService;
import itmo.gorshkov.util.CustomGsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityNotFoundException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.List;

import static itmo.gorshkov.util.WriteErrorUtil.writeError;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;

@WebServlet("/api/band/*")
public class MusicBandController extends HttpServlet {
    private final MusicBandService musicBandService;
    private final Gson gson;

    public static Logger logger = LoggerFactory.getLogger(MusicBandRepositoryImpl.class);

    public MusicBandController() {
        this.gson = CustomGsonBuilder.create();
        this.musicBandService = new MusicBandService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        PrintWriter writer = resp.getWriter();

        Integer id = (Integer) req.getAttribute("id");

        if (req.getPathInfo() != null && req.getPathInfo().equals("/count_by/number_of_participants")) {
            writer.write(gson.toJson(musicBandService.countByNumberOfParticipants()));
        } else if (id != null) {
            MusicBand band = musicBandService.findById(id);

            if (band != null) {
                writer.write(gson.toJson(band));
            } else {
                resp.setStatus(SC_NOT_FOUND);
            }

        } else {
            FilterConfiguration filterConfiguration = new FilterConfiguration();
            if (req.getAttribute("count") != null && req.getAttribute("page") != null) {
                filterConfiguration.setCount((int) req.getAttribute("count"));
                filterConfiguration.setPage((int) req.getAttribute("page"));
                logger.info("count: {}, page: {}", filterConfiguration.getCount(), filterConfiguration.getPage());
            }

            if (req.getParameter("order") != null) {
                filterConfiguration.setOrder(req.getParameterValues("order"));
            }

            if (req.getParameter("filter") != null) {
                filterConfiguration.setFilter(req.getParameterValues("filter"));
            }

            List<MusicBand> bands = null;
            try {
                bands = musicBandService.findAll(filterConfiguration);
            } catch (ParseException e) {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                logger.error(e.getMessage(), e);
            }

            if (bands != null) {
                writer.write(gson.toJson(bands));
            } else {
                resp.setStatus(SC_NOT_FOUND);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        PrintWriter writer = resp.getWriter();

        MusicBand band = (MusicBand) req.getAttribute("band");

        MusicBand savedValue = null;
        try {
            savedValue = musicBandService.save(band);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            writer.write(gson.toJson(savedValue));
        } catch (IllegalArgumentException e) {
            writeError(resp, SC_BAD_REQUEST, "Entity with " + e.getMessage() + " already exist");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        PrintWriter writer = resp.getWriter();

        MusicBand band = (MusicBand) req.getAttribute("band");

        try {
            writer.write(gson.toJson(musicBandService.update(band)));
        } catch (EntityNotFoundException e) {
            writeError(resp, SC_NOT_FOUND, "Entity with " + e.getMessage() + " not found");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        Integer id = (Integer) req.getAttribute("id");
        try {
            musicBandService.delete(id);
        } catch (EntityNotFoundException e) {
            writeError(resp, SC_NOT_FOUND, "Entity with " + e.getMessage() + " not found");
        }
    }
}
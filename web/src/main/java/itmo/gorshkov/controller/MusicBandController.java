package itmo.gorshkov.controller;

import itmo.gorshkov.ContextProvider;
import itmo.gorshkov.config.FilterConfiguration;
import itmo.gorshkov.entity.MusicBand;
import itmo.gorshkov.service.MusicBandService;
import itmo.gorshkov.validator.MusicBandValidator;
import itmo.gorshkov.validator.OptionValidator;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.List;

import static itmo.gorshkov.util.ResponseUtil.errorResponse;

@Path("/band")
@Consumes({ "application/json" })
@Produces({ "application/json" })
public class MusicBandController {
    private final MusicBandService bandService;
    private final MusicBandValidator bandValidator;
    private final OptionValidator optionValidator;

    public MusicBandController() {
        ContextProvider contextProvider = new ContextProvider();
        this.bandService = contextProvider.lookupMusicBandService();
        this.bandValidator = new MusicBandValidator();
        this.optionValidator = new OptionValidator();
    }

    @GET
    @Path("")
    public Response getBands(@QueryParam("count") Integer count, @QueryParam("page") Integer page,
                              @QueryParam("order") List<String> order, @QueryParam("filter") List<String> filter) {
        FilterConfiguration filterConfiguration = createFilter(count, page, order, filter);

        optionValidator.validate(filterConfiguration);

        List<MusicBand> bands = bandService.findAll(filterConfiguration);

        if (bands.size() > 0) {
            return Response.status(HttpServletResponse.SC_OK).entity(bands).build();
        } else {
            return Response.status(HttpServletResponse.SC_NOT_FOUND).build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getBand(@PathParam("id") Integer id) {
        MusicBand band = bandService.findById(id);

        if (band != null) {
            return Response.status(HttpServletResponse.SC_OK).entity(band).build();
        } else {
            return Response.status(HttpServletResponse.SC_NOT_FOUND).build();
        }
    }

    @POST
    @Path("")
    public Response addBand(String json) {
        MusicBand band = bandValidator.validate(json);

        if (band.getId() != null) {
            throw new BadRequestException(errorResponse("id shouldn't present in request body"));
        }



        MusicBand savedValue = bandService.save(band);
        return Response.status(HttpServletResponse.SC_CREATED).entity(savedValue).build();
    }

    @PUT
    @Path("")
    public Response putBand(String json) {
        MusicBand band = bandValidator.validate(json);

        if (band.getId() == null) {
            throw new BadRequestException(errorResponse("id must present in request body"));
        }


        MusicBand savedValue = bandService.update(band);
        return Response.status(HttpServletResponse.SC_OK).entity(savedValue).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteBand(@PathParam("id") Integer id) {
        bandService.delete(id);
        return Response.status(HttpServletResponse.SC_OK).build();
    }

    @GET
    @Path("/count_by/number_of_participants")
    public Response getGoldenPalmCount() {
        return Response.status(HttpServletResponse.SC_OK).entity(bandService.countByNumberOfParticipants()).build();
    }

    private FilterConfiguration createFilter(Integer count, Integer page, List<String> order, List<String> filter) {
        FilterConfiguration filterConfiguration = new FilterConfiguration();
        if (count != null && page != null) {
            filterConfiguration.setCount(count);
            filterConfiguration.setPage(page);
        }

        if (order != null && order.size() > 0) {
            filterConfiguration.setOrder(order);
        }

        if (filter != null && filter.size() > 0) {
            filterConfiguration.setFilter(filter);
        }
        return filterConfiguration;
    }
}
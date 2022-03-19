package itmo.gorshkov;

import com.google.gson.Gson;
import itmo.gorshkov.config.FilterConfiguration;
import itmo.gorshkov.entity.MusicBand;
import itmo.gorshkov.service.MusicBandService;
import itmo.gorshkov.util.CountByResult;
import itmo.gorshkov.util.CustomGsonBuilder;
import itmo.gorshkov.validator.MusicBandValidator;
import itmo.gorshkov.validator.OptionValidator;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.Arrays;
import java.util.List;

@Stateless
@WebService(
        targetNamespace = "http://localhost:8090/v1/api/band"
)
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public class MusicBandController {
    private final MusicBandService bandService;
    private final MusicBandValidator bandValidator;
    private final OptionValidator optionValidator;
    private final Gson gson = CustomGsonBuilder.create();

    public MusicBandController() {
        ContextProvider contextProvider = new ContextProvider();
        this.bandService = contextProvider.lookupMusicBandService();
        this.bandValidator = new MusicBandValidator();
        this.optionValidator = new OptionValidator();
    }

    @WebMethod
    public List<MusicBand> getBands(@WebParam(name = "filter") Filter filter) {
        FilterConfiguration filterConfiguration = createFilter(filter);

        optionValidator.validate(filterConfiguration);

        List<MusicBand> bands = bandService.findAll(filterConfiguration);

        if (bands.size() > 0) {
            return bands;
        } else {
            throw new RuntimeException("not found bands");
        }
    }

    @WebMethod
    public MusicBand getBand(@WebParam(name = "id") Integer id) {
        MusicBand band = bandService.findById(id);

        if (band != null) {
            return band;
        } else {
            throw new RuntimeException("band not found");
        }
    }

    @WebMethod
    public MusicBand addBand(String json) {
        MusicBand band = bandValidator.validate(json);

        if (band.getId() != null) {
            throw new RuntimeException("id shouldn't present in request body");
        }

        return bandService.save(band);
    }

    @WebMethod
    public MusicBand putBand(String json) {
        MusicBand band = bandValidator.validate(json);

        if (band.getId() == null) {
            throw new RuntimeException("id must present in request body");
        }

        return bandService.update(band);
    }

    @WebMethod
    public Integer deleteBand(@WebParam(name = "deleteId") Integer id) {
        bandService.delete(id);
        return id;
    }

    @WebMethod
    public List<CountByResult> getGoldenPalmCount() {
        return bandService.countByNumberOfParticipants();
    }

    private FilterConfiguration createFilter(Filter filter) {
        FilterConfiguration filterConfiguration = new FilterConfiguration();
        if (filter.getCount() != null && filter.getPage() != null && filter.getCount() != 0 && filter.getPage() != 0) {
            filterConfiguration.setCount(filter.getCount());
            filterConfiguration.setPage(filter.getPage());
        }

        if (filter.getOrder() != null && !filter.getOrder().isEmpty()) {
            filterConfiguration.setOrder(Arrays.asList(filter.getOrder().split(";")));
        }

        if (filter.getFilter() != null && !filter.getFilter().isEmpty()) {
            filterConfiguration.setFilter(Arrays.asList(filter.getFilter().split(";")));
        }
        return filterConfiguration;
    }
}
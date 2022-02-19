package itmo.gorshkov.repository;



import itmo.gorshkov.config.FilterConfiguration;
import itmo.gorshkov.entity.MusicBand;

import java.text.ParseException;
import java.util.List;

public interface MusicBandRepository {
    List<MusicBand> findAll();

    List<MusicBand> findAll(FilterConfiguration filterConfiguration) throws ParseException;

    void save(MusicBand coordinates);

    MusicBand findById(Integer id);

    MusicBand update(MusicBand newValue);

    void delete(Integer id);

    List<Object[]> countByNumberOfParticipants();

}

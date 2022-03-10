package itmo.gorshkov.repository;


import itmo.gorshkov.config.FilterConfiguration;
import itmo.gorshkov.entity.MusicBand;

import java.util.List;

public interface MusicBandRepository {
    List<MusicBand> findAll();

    List<MusicBand> findAll(FilterConfiguration filterConfiguration);

    void save(MusicBand coordinates);

    MusicBand findById(Integer id);

    MusicBand update(MusicBand newValue);

    void delete(Integer id);

    List<Object[]> countByNumberOfParticipants();

}

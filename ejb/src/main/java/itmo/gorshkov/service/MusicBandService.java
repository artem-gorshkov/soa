package itmo.gorshkov.service;

import itmo.gorshkov.config.FilterConfiguration;
import itmo.gorshkov.entity.MusicBand;
import itmo.gorshkov.util.CountByResult;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface MusicBandService {
    List<MusicBand> findAll(FilterConfiguration filterConfiguration);

    MusicBand save(MusicBand musicBand);

    MusicBand update(MusicBand musicBand);

    MusicBand findById(Integer id);

    void delete(Integer id);

    List<CountByResult> countByNumberOfParticipants();
}

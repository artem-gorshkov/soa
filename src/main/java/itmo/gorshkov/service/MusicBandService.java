package itmo.gorshkov.service;

import itmo.gorshkov.config.FilterConfiguration;
import itmo.gorshkov.entity.MusicBand;
import itmo.gorshkov.repository.MusicBandRepository;
import itmo.gorshkov.repository.MusicBandRepositoryImpl;
import itmo.gorshkov.util.CountByResult;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

public class MusicBandService {
    private final MusicBandRepository musicBandRepository;

    public MusicBandService() {
        this.musicBandRepository = new MusicBandRepositoryImpl();
    }

    public List<MusicBand> findAll(FilterConfiguration filterConfiguration) throws ParseException {
        return musicBandRepository.findAll(filterConfiguration);
    }

    public MusicBand saveOrUpdate(MusicBand musicBand) {
        if (musicBand.getId() == null || musicBandRepository.findById(musicBand.getId()) == null) {
            musicBandRepository.save(musicBand);
            return musicBand;
        } else {
            return musicBandRepository.update(musicBand);
        }
    }

    public MusicBand findById(Integer id) {
        return musicBandRepository.findById(id);
    }

    public void delete(Integer id) {
        musicBandRepository.delete(id);
    }

    public List<CountByResult> countByNumberOfParticipants() {
        return musicBandRepository.countByNumberOfParticipants().stream()
                .map(row -> new CountByResult((int) row[0], (java.math.BigInteger) row[1]))
                .collect(Collectors.toList());
    }
    //TODO: Сгруппировать объекты по значению поля numberOfParticipants, вернуть количество элементов в каждой группе.
}

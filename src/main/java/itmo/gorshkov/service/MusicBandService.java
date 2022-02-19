package itmo.gorshkov.service;

import itmo.gorshkov.config.FilterConfiguration;
import itmo.gorshkov.entity.MusicBand;
import itmo.gorshkov.repository.MusicBandRepository;
import itmo.gorshkov.repository.MusicBandRepositoryImpl;
import itmo.gorshkov.util.CountByResult;

import javax.persistence.EntityNotFoundException;
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

    public MusicBand save(MusicBand musicBand) {
        if (musicBand.getId() == null || musicBandRepository.findById(musicBand.getId()) == null) {
            musicBandRepository.save(musicBand);
            return musicBand;
        } else {
            throw new IllegalArgumentException(musicBand.getId().toString());
        }
    }

    public MusicBand update(MusicBand musicBand) {
        if (musicBand.getId() == null || musicBandRepository.findById(musicBand.getId()) == null) {
            throw new EntityNotFoundException(musicBand.getId().toString());
        }
        return musicBandRepository.update(musicBand);
    }

    public MusicBand findById(Integer id) {
        return musicBandRepository.findById(id);
    }

    public void delete(Integer id) {
        if (id == null || musicBandRepository.findById(id) == null) {
            throw new EntityNotFoundException("" + id);
        }
        musicBandRepository.delete(id);
    }

    public List<CountByResult> countByNumberOfParticipants() {
        return musicBandRepository.countByNumberOfParticipants().stream()
                .map(row -> new CountByResult((int) row[0], (java.math.BigInteger) row[1]))
                .collect(Collectors.toList());
    }
}

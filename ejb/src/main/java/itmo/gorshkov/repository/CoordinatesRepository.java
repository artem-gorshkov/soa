package itmo.gorshkov.repository;


import itmo.gorshkov.entity.Coordinates;

import java.util.List;

public interface CoordinatesRepository {
    List<Coordinates> findAll();

    void save(Coordinates coordinates);

    Coordinates findById(Integer id);

    Coordinates update(Coordinates newValue);

    void delete(Integer id);
}

package itmo.gorshkov.repository;


import itmo.gorshkov.entity.Coordinates;
import itmo.gorshkov.entity.Label;

import java.util.List;

public interface LabelRepository {
    List<Label> findAll();

    void save(Label coordinates);

    Label findById(String name);

    Label update(Label newValue);

    void delete(String name);
}

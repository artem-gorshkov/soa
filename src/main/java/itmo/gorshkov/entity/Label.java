package itmo.gorshkov.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Label {
    @Id
    private String name;

    public String getName() {
        return name;
    }

    public Label setName(String name) {
        this.name = name;
        return this;
    }
}

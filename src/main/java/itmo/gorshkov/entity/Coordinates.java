package itmo.gorshkov.entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Coordinates {
    @Id
    @GeneratedValue
    private int id;
    private float x; //Значение поля должно быть больше -331
    private float y; //Значение поля должно быть больше -320

    public int getId() {
        return id;
    }

    public Coordinates setId(int id) {
        this.id = id;
        return this;
    }

    public float getX() {
        return x;
    }

    public Coordinates setX(float x) {
        this.x = x;
        return this;
    }

    public float getY() {
        return y;
    }

    public Coordinates setY(float y) {
        this.y = y;
        return this;
    }
}

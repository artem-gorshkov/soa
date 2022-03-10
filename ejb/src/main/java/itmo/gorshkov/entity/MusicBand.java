package itmo.gorshkov.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "MUSIC_BAND")
public class MusicBand implements Serializable {
    private static final long serialVersionUID = -2407173236798550497L;
    @Id
    @Min(value = 1, message = "id must present and be greater than 0")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    @NotBlank(message = "name must present and be not blank")
    private String name; //Поле не может быть null, Строка не может быть пустой
    private BigDecimal x; //Значение поля должно быть больше -331
    private BigDecimal y; //Значение поля должно быть больше -320
    @Column(name = "CREATION_DATE")
    @NotNull(message = "creationDate must present")
    private java.time.LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    @Min(value = 1, message = "numberOfParticipants must be greater than 0")
    @NotNull(message = "numberOfParticipants must present")
    @Column(name = "NUMBER_OF_PARTICIPANTS")
    private Integer numberOfParticipants; //Поле не может быть null, Значение поля должно быть больше 0
    @Min(value = 1, message = "albumsCount must be greater than 0")
    @Column(name = "ALBUMS_COUNT")
    private Long albumsCount; //Значение поля должно быть больше 0
    @Enumerated(EnumType.STRING)
    private MusicGenre genre; //Поле может быть null
    @Column(name = "LABEL_NAME")
    private String labelName; //Поле может быть null

    @AssertTrue(message = "x must present and be greater than -331")
    public boolean getXCheck() {
        if (x == null) {
            return false;
        }

        return BigDecimal.valueOf(-331).compareTo(x) < 0;
    }

    @AssertTrue(message = "y must present and be greater than -320")
    public boolean getYCheck() {
        if (y == null) {
            return false;
        }
        return BigDecimal.valueOf(-320).compareTo(y) < 0;
    }

    /**
     * @return Возвращает лист с именами полей класса
     */
    public static List<String> getMusicBandFieldNames() {
        ArrayList<String> fieldNames = new ArrayList<>();
        for (Field field : MusicBand.class.getDeclaredFields()) {
            fieldNames.add(field.getName());
        }
        return fieldNames;
    }

    public Integer getId() {
        return id;
    }

    public MusicBand setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public MusicBand setName(String name) {
        this.name = name;
        return this;
    }

    public BigDecimal getX() {
        return x;
    }

    public MusicBand setX(BigDecimal x) {
        this.x = x;
        return this;
    }

    public BigDecimal getY() {
        return y;
    }

    public MusicBand setY(BigDecimal y) {
        this.y = y;
        return this;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public MusicBand setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public Integer getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public MusicBand setNumberOfParticipants(Integer numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
        return this;
    }

    public Long getAlbumsCount() {
        return albumsCount;
    }

    public MusicBand setAlbumsCount(Long albumsCount) {
        this.albumsCount = albumsCount;
        return this;
    }

    public MusicGenre getGenre() {
        return genre;
    }

    public MusicBand setGenre(MusicGenre genre) {
        this.genre = genre;
        return this;
    }

    public String getLabelName() {
        return labelName;
    }

    public MusicBand setLabelName(String labelName) {
        this.labelName = labelName;
        return this;
    }
}

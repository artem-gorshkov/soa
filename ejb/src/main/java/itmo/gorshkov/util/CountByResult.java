package itmo.gorshkov.util;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class CountByResult implements Serializable {
    private static final long serialVersionUID = 8404804323002607050L;

    private int numberOfParticipants;
    private java.math.BigInteger count;
}

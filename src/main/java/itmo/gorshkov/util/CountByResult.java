package itmo.gorshkov.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CountByResult {
    private int numberOfParticipants;
    private java.math.BigInteger count;
}

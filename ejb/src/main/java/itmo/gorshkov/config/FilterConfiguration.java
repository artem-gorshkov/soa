package itmo.gorshkov.config;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class FilterConfiguration implements Serializable {
    private static final long serialVersionUID = 6433175772792362986L;

    private Integer count;
    private Integer page;
    private List<String> order;
    private List<String> filter;
}

package itmo.gorshkov;

import lombok.Data;

@Data
public class ServiceDiscoveryDto {
    private String id;
    private String name;
    private String address;
    private int port;
    private boolean enableTagOverride;
}

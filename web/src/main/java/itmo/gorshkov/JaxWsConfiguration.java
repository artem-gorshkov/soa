package itmo.gorshkov;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationPath("v1/api")
public class JaxWsConfiguration extends Application {

    public JaxWsConfiguration() throws Exception {
        super();

        Client client = ClientBuilder.newBuilder().build();
        String sdUrl = "http://localhost:8500";

        ServiceDiscoveryDto configuration = new ServiceDiscoveryDto();
        configuration.setId("l1");
        configuration.setName("LAB1");
        configuration.setAddress("localhost");
        configuration.setPort(9443);

        Response response = client
                .target(sdUrl + "/v1/agent/service/register")
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(configuration, MediaType.APPLICATION_JSON));

        if (response.getStatus() != 200) {
            throw new Exception("Failed to register service in Service discovery");
        }
    }
}

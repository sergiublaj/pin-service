package ro.nexttech.pinservice.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class ApiResource extends ResourceConfig {
    public ApiResource() {
        packages("ro.nexttech.pinservice.api.endpoints");
    }
}

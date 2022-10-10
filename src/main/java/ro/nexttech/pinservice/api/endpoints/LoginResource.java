package ro.nexttech.pinservice.api.endpoints;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Slf4j
@Component
@Path(ResourcesPath.AUTH)
@Consumes(APPLICATION_JSON)
@Tag(name = "resources:loginResource")
@CrossOrigin(origins = "*")
public class LoginResource {

    @POST
    @Path(ResourcesPath.LOGIN)
    public Response login() {
        return Response.ok().build();
    }
}

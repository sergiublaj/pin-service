package ro.nexttech.pinservice.api.endpoints;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import ro.nexttech.pinservice.api.models.UserDTO;
import ro.nexttech.pinservice.services.UserService;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Slf4j
@Component
@Path(ResourcesPath.API)
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
@Tag(name = "resources:loginResource")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UserResource {
    private final UserService userService;

    @GET
    @Path(ResourcesPath.DETAILS)
    public Response details() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            log.info("Getting details for user {}", authentication.getPrincipal());

            return Response.ok().entity(userService.getUserByEmail(authentication.getPrincipal().toString())).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path(ResourcesPath.USERS)
    public Response getUsers() {
        try {
            log.info("Getting all users");

            return Response.ok().entity(userService.getUsers()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Path(ResourcesPath.USERS)
    public Response saveUser(UserDTO user) {
        try {
            log.info("Saving user {}", user);

            return Response.ok().entity(userService.saveUser(user)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}

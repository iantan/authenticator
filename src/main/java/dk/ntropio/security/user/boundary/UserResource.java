package dk.ntropio.security.user.boundary;

import dk.ntropio.security.user.control.UserController;
import dk.ntropio.security.user.entity.User;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.net.URI;

@Path("/api/users")
public class UserResource {

    @GET
    @RolesAllowed("user")
    @Path("/login")
    public String login(@Context SecurityContext securityContext) {
        return securityContext.getUserPrincipal().getName();
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response create(User user) {
        user = UserController.addUser(user.userName, user.password);
        return Response.created(URI.create("/api/users/" + user.id)).build();
    }

    @GET
    @Path("/validate-user/{username}")
    @PermitAll
    public Response validateUser(@PathParam("username") String userName) {
        return Response.ok(UserController.isValidUserName(userName)).build();
    }

    @POST
    @Path("/change-password")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("user")
    public Response changePassword(User user) {
        UserController.changePassword(user.userName, user.password);
        return Response.ok().build();
    }

}
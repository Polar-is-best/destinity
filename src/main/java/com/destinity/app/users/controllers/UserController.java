package com.destinity.app.users.controllers;

import com.destinity.app.users.dto.UserDTO;
import com.destinity.app.users.model.User;
import com.destinity.app.users.services.UserService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserController {

    @Inject
    private UserService userService;
    
    @POST
    public Response addUser(User user) {
        if (user.getFirstName() == null || user.getLastName() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Nombre y apellido paterno son requeridos").build();
        }
        userService.registerUser(user);
        return Response.status(Response.Status.CREATED).entity("Usuario agregado satisfactoriamente").build();
    }

    @GET
    public List<UserDTO> getUsers() {
        return userService.listUsers();
    }
    
    @GET
    @Path("/{id}")
    public Response getUserById(@PathParam("id") String id) {
        Optional<UserDTO> user = userService.getUserById(new ObjectId(id));
        return user.map(Response::ok)
                .orElse(Response.status(Response.Status.NOT_FOUND))
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response updateUser(@PathParam("id") String id, User user) {
        user.setId(new ObjectId(id));
        userService.updateUser(user);
        return Response.ok(new UserDTO(user)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") String id) {
        userService.deleteUser(new ObjectId(id));
        return Response.noContent().build();
    }
}

package com.paf;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;

import javax.annotation.security.DenyAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import beans.UserBean;
import model.User;
import model.Authentication;

@Path("/users")
public class UserService {

    User userObj = new User();

    @RolesAllowed({"admin"})
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readUsers() {

        List <UserBean> list;
        Response response;

        list = userObj.readUsers();
        response=Response.ok(userObj.readUsers()).build();

        if (!list.isEmpty()) {

            return response;

        }

        return Response.noContent().build();

    }

    @RolesAllowed("admin")
    @GET
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readUserById(@PathParam("userId") int id) {

        UserBean user = userObj.readUserById(id);

        if (user !=null) {

            return	Response.ok().entity(userObj.readUserById(id)).build();

        }

        return	Response.noContent().build();

    }

    @RolesAllowed({"admin"})
    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertUsers(UserBean user) {

        Response response =	userObj.insertUsers(user);
        return response;

    }

    @RolesAllowed({"admin"})
    @PUT
    @Path("/{userId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("userId") int userId,UserBean user) {

        user.setId(userId);
        return userObj.updateUser(user);

    }

    @RolesAllowed({"admin"})
    @DELETE
    @Path("/{userId}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteUser(@PathParam("userId") int userId) {

        return userObj.deleteUser(userId);

    }

    @RolesAllowed({"doctor"})
    @GET
    @Path("/doctor")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean checkDoc() {

        return true;

    }

    @RolesAllowed({"admin"})
    @GET
    @Path("/admin")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean checkAdmin() {

        return true;

    }

    @RolesAllowed({"user"})
    @GET
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean checkPatient() {

        return true;

    }

    @DenyAll
    @GET
    @Path("/deny")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean deny() {

        return true;

    }

}

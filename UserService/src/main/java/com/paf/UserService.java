package com.paf;

import beans.UserBean;
import model.User;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/users")
public class UserService {

    User usrObj = new User();

    @RolesAllowed({"admin"})
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readUsers() {
        List <UserBean> list;
        Response response;

        list = usrObj.readUsers();
        response=Response.ok(usrObj.readUsers()).build();

        if (!list.isEmpty()) {

            return response;

        }

        return Response.noContent().build();

    }

    @RolesAllowed("admin")
    @GET
    @Path("/{userID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readUserById(@PathParam("userID") int id) {

        UserBean user = usrObj.readUserById(id);

        if (user !=null) {

            return Response.ok().entity(usrObj.readUserById(id)).build();

        }

        return Response.noContent().build();

    }

    @RolesAllowed({"admin"})
    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertUsers(UserBean user) {

        Response response = usrObj.insertUsers(user);
        return response;

    }

    @RolesAllowed({"admin"})
    @PUT
    @Path("/{userId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("userId") int userId,UserBean user) {

        user.setId(userId);
        return usrObj.updateUser(user);

    }

    @RolesAllowed({"admin"})
    @DELETE
    @Path("/{userId}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteUser(@PathParam("userId") int userId) {

        return usrObj.deleteUser(userId);

    }

    @RolesAllowed({"doctor","admin"})
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

    @RolesAllowed({"user","admin"})
    @GET
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean checkUser() {

        return true;

    }

}

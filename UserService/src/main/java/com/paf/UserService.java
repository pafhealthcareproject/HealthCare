package com.paf;

import beans.UserBean;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.User;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/Users")

public class UserService {

    User userObj = new User();

    @RolesAllowed("admin")
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserBean> readUser() {

        return userObj.readUser();

    }

    @RolesAllowed({ "admin", "user" })
    @GET
    @Path("/{userID}")
    // @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UserBean readUserById(@PathParam("userID") int id) {

        return userObj.readUserById(id);

    }

    @RolesAllowed({ "admin", "user" })
    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String insertUser(String userData) {

        UserBean user = new UserBean(userData);
        String output = userObj.insertUser(user);

        return output;

    }

    @RolesAllowed({ "admin", "user" })
    @PUT
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String updateUser(String userData) {

        UserBean user = new UserBean(userData);
        String output = userObj.updateUser(user);

        return output;

    }

    @RolesAllowed({ "admin", "user" })
    @DELETE
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String deleteUser(String userData) {

        JsonObject UserObject = new JsonParser().parse(userData).getAsJsonObject();

        String userID = UserObject.get("userID").getAsString();

        String output = userObj.deleteUser(userID);
        return output;

    }

}

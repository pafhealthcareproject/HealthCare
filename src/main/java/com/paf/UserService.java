package com.paf;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/Users")
public class UserService {

    User userObj = new User();

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public String readUser() {

        return userObj.readUser();

    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String insertUser(String userData) {

        JsonObject userObject = new JsonParser().parse(userData).getAsJsonObject();

        String firstName = userObject.get("firstName").getAsString();
        String lastName = userObject.get("lastName").getAsString();
        String age = userObject.get("age").getAsString();
        String gender = userObject.get("gender").getAsString();
        String email = userObject.get("email").getAsString();
        String address = userObject.get("address").getAsString();
        String username = userObject.get("username").getAsString();
        String password = userObject.get("password").getAsString();
        String userPhone = userObject.get("userPhone").getAsString();

        String output = userObj.insertUser(firstName, lastName, age, gender, email, address, username, password, userPhone);

        return output;

    }

    @PUT
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String updateUser(String userData) {

        JsonObject userObject = new JsonParser().parse(userData).getAsJsonObject();

        String userID = userObject.get("userID").getAsString();
        String firstName = userObject.get("firstName").getAsString();
        String lastName = userObject.get("lastName").getAsString();
        String age = userObject.get("age").getAsString();
        String gender = userObject.get("gender").getAsString();
        String email = userObject.get("email").getAsString();
        String address = userObject.get("address").getAsString();
        String username = userObject.get("username").getAsString();
        String password = userObject.get("password").getAsString();
        String userPhone = userObject.get("userPhone").getAsString();

        String output = userObj.updateUser(userID, firstName, lastName, age, gender, email, address, username, password, userPhone);

        return output;

    }

    @DELETE
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String deleteUser(String userData) {

        JsonObject userObject = new JsonParser().parse(userData).getAsJsonObject();

        String userID = userObject.get("userID").getAsString();

        String output = userObj.deleteUser(userID);

        return output;

    }

}

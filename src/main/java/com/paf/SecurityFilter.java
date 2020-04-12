package com.paf;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Base64;
import java.util.List;
import java.util.StringTokenizer;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import model.Authentication;
import util.DBConnection;

//@Provider
public class SecurityFilter implements ContainerRequestFilter {

    public static final String AUTHENTICATION_HEADER_KEY = "Authorization";
    public static final String AUTHENTICATION_HEADER_PREFIX = "Basic ";

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        // TODO Auto-generated method stub
        List<String> authHeader = requestContext.getHeaders().get(AUTHENTICATION_HEADER_KEY);

        if(authHeader != null && authHeader.size() > 0 ) {

            String authToken = authHeader.get(0);
            authToken = authToken.replaceFirst(AUTHENTICATION_HEADER_PREFIX, "");

            String decodedString = "";

            try {

                byte[] decodedBytes = Base64.getDecoder().decode(authToken);
                decodedString = new String(decodedBytes, "UTF-8");

            } catch (IOException e) {

                e.printStackTrace();

            }

            final StringTokenizer tokenizer = new StringTokenizer(decodedString, ":");
            final String username = tokenizer.nextToken();
            final String password = tokenizer.nextToken();

            if(Authentication.auth(username, password)){

                return;

            }

        }

        Response unauthoriazedStatus = Response.status(Response.Status.UNAUTHORIZED).entity("{\"Error\" : \"Authorization Failed\"}").build();
        requestContext.abortWith(unauthoriazedStatus);

    }

}

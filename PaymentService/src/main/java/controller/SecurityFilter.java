package controller;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

@Provider
public class SecurityFilter implements ContainerRequestFilter {

    public static final String AUTHENTICATION_HEADER_KEY = "Authorization";
    public static final String AUTHENTICATION_HEADER_PREFIX = "Basic ";

    @Context
    private ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        // TODO Auto-generated method stub
        List<String> authHeader = requestContext.getHeaders().get(AUTHENTICATION_HEADER_KEY);
        Method method = resourceInfo.getResourceMethod();

        if (!method.isAnnotationPresent(PermitAll.class)) {

            if (method.isAnnotationPresent(DenyAll.class)) {
                Response unauthorizedStatus = Response.status(Response.Status.UNAUTHORIZED).entity("{\"Error\" : \"Not Allowed\"}").build();
                requestContext.abortWith(unauthorizedStatus);

            }

            if (authHeader != null && authHeader.size() > 0) {

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

                if (method.isAnnotationPresent(RolesAllowed.class)) {

                    RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
                    Set<String> rolesSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));

                    ClientConfig clientConfig = new ClientConfig();
                    HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(username, password);
                    clientConfig.register(feature);
                    clientConfig.register(JacksonFeature.class);

                    Client client = ClientBuilder.newClient(clientConfig);
                    WebTarget webTarget;

                    if (rolesSet.contains("user")) {
                        webTarget = client.target("http://localhost:8080/UserService/UserService").path("users/user");
                    } else if (rolesSet.contains("doctor")) {
                        webTarget = client.target("http://localhost:8080/UserService/UserService").path("users/doctor");
                    }
                    else if (rolesSet.contains("hospital")) {
                        webTarget = client.target("http://localhost:8080/UserService/UserService").path("users/hospital");
                    } else if (rolesSet.contains("admin")) {
                        webTarget = client.target("http://localhost:8080/UserService/UserService").path("users/admin");
                    } else {
                        webTarget = client.target("http://localhost:8080/UserService/UserService").path("users/deny");
                    }

                    Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);

                    Response response = invocationBuilder.get();

                    if (response.getStatus() != 200) {

                        Response unauthorizedStatus = Response.status(Response.Status.UNAUTHORIZED).entity("{\"Error\" : \"Not Authorized 3\"}").build();
                        requestContext.abortWith(unauthorizedStatus);

                    }

                    return;

                }

            }

        }

        Response unauthorizedStatus = Response.status(Response.Status.UNAUTHORIZED).entity("{\"Error\" : \"Not Authorized\"}").build();
        requestContext.abortWith(unauthorizedStatus);

    }

}

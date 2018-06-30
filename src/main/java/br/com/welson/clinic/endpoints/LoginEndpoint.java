package br.com.welson.clinic.endpoints;

import br.com.welson.clinic.annotations.Protect;
import br.com.welson.clinic.persistence.model.Admin;
import br.com.welson.clinic.persistence.model.ApplicationUser;
import br.com.welson.clinic.persistence.model.Doctor;
import br.com.welson.clinic.security.Authentication;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("login")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class LoginEndpoint {

    @Inject
    private Authentication authentication;

    @POST
    public Response login(ApplicationUser applicationUser) {
        return authentication.authenticate(applicationUser.getUsername(), applicationUser.getPassword());
    }

    @GET
    @Protect({Doctor.class})
    public Response test(@Context HttpHeaders httpHeaders) {
        return Response.ok("{\"message\":\"Voce está logado corretamente!\"}", MediaType.APPLICATION_JSON).build();
    }
}

package br.com.welson.clinic.errors;

import javax.ws.rs.core.Response;

public class DefaultError extends ErrorDetails {

    public DefaultError(String message, Response.Status status) {
        super(message, Response.Status.INTERNAL_SERVER_ERROR);
    }

    public Response getResponse() {
        return Response.status(getStatusCode()).entity(this).build();
    }
}

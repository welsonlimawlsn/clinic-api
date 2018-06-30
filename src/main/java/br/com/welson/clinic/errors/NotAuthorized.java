package br.com.welson.clinic.errors;

import javax.ws.rs.core.Response;

public class NotAuthorized extends ErrorDetails {

    public NotAuthorized() {
        super("Não autorizado", Response.Status.UNAUTHORIZED);
    }
}

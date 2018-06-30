package br.com.welson.clinic.errors;

import javax.ws.rs.core.Response;

public class Forbidden extends ErrorDetails {

    public Forbidden() {
        super("Não autorizado", Response.Status.FORBIDDEN);
    }
}

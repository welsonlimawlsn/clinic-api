package br.com.welson.clinic.errors;

import javax.ws.rs.core.Response;

public class UsernameOrPasswordError extends ErrorDetails {

    public UsernameOrPasswordError() {
        super("Usuário ou senha invalidos!", Response.Status.FORBIDDEN);
    }
}

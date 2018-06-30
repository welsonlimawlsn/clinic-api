package br.com.welson.clinic.interceptors;

import br.com.welson.clinic.annotations.Protect;
import br.com.welson.clinic.errors.DefaultError;
import br.com.welson.clinic.errors.ErrorDetails;
import br.com.welson.clinic.errors.Forbidden;
import br.com.welson.clinic.errors.NotAuthorized;
import br.com.welson.clinic.persistence.model.ApplicationUser;
import br.com.welson.clinic.persistence.model.User;
import br.com.welson.clinic.security.Authorization;
import br.com.welson.clinic.security.SecurityConstants;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

@Interceptor
@Protect
public class ProtectInterceptor {

    @Inject
    private Authorization authorization;

    @AroundInvoke
    public Object invoke(InvocationContext invocationContext) {
        Class<? extends User>[] permit = invocationContext.getMethod().getAnnotation(Protect.class).value();
        String authorizationToken = getAuthorization(invocationContext.getParameters());
        if (authorizationToken == null || !authorizationToken.startsWith(SecurityConstants.PREFIX)) {
            return Response.status(401).entity(new NotAuthorized()).build();
        }
        ApplicationUser authorized = this.authorization.getAuthorized(authorizationToken);
        if (authorized == null) {
            return Response.status(403).entity(new Forbidden()).build();
        }
        if (!isAllowed(permit, authorized.getUser())) {
            return Response.status(403).entity(new Forbidden()).build();
        }
        Object o = null;
        try {
            o = invocationContext.proceed();
        } catch (Exception e) {
            e.printStackTrace();
            return new DefaultError(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR).getResponse();

        }
        return o;
    }

    private String getAuthorization(Object[] parameters) {
        HttpHeaders httpHeaders = null;
        for (Object o : parameters) {
            if (o instanceof HttpHeaders) {
                httpHeaders = (HttpHeaders) o;
            }
        }
        return httpHeaders != null ? httpHeaders.getHeaderString("Authorization") : null;
    }

    private boolean isAllowed(Class<? extends User>[] permit, User user) {
        boolean isAllowed = false;
        for (Class<? extends User> userType : permit) {
            if (user.getClass().getName().equals(userType.getName())) {
                isAllowed = true;
            }
        }
        return isAllowed;
    }
}

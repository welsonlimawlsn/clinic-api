package br.com.welson.clinic.security;

import br.com.welson.clinic.annotations.Transactional;
import br.com.welson.clinic.errors.UsernameOrPasswordError;
import br.com.welson.clinic.persistence.dao.DAO;
import br.com.welson.clinic.persistence.model.AccessToken;
import br.com.welson.clinic.persistence.model.ApplicationUser;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static br.com.welson.clinic.security.SecurityConstants.*;
import static io.jsonwebtoken.Jwts.builder;
import static java.time.ZonedDateTime.now;
import static java.util.Date.from;

@RequestScoped
public class Authentication {

    @Inject
    private DAO<ApplicationUser> applicationUserDAO;

    public Response authenticate(String username, String password) {
        ApplicationUser applicationUser = getApplicationUserByUsernameAndPassword(username, password);
        if (applicationUser == null) {
            return Response.status(Response.Status.FORBIDDEN).entity(new UsernameOrPasswordError()).build();
        }
        applicationUser.setAccessToken(generateToken(applicationUser));
        return Response.ok(applicationUser).build();
    }

    private AccessToken generateToken(ApplicationUser applicationUser) {
        ZonedDateTime expirationTime = now(ZoneOffset.UTC).plus(EXPIRATION_TIME_IN_HOURS, ChronoUnit.HOURS);
        String token = PREFIX + builder().setSubject(applicationUser.getUsername()).setExpiration(from(expirationTime.toInstant())).signWith(SignatureAlgorithm.HS256, SECRET).compact();
        return new AccessToken(token, expirationTime.toLocalDateTime());
    }

    private ApplicationUser getApplicationUserByUsernameAndPassword(String username, String password) {
        List<ApplicationUser> applicationUsers = applicationUserDAO.findByHQLQuery(0, "applicationUserByUsernameAndPassword", username, password);
        return applicationUsers.size() == 1 ? applicationUsers.get(0) : null;
    }
}

package br.com.welson.clinic.security;

import br.com.welson.clinic.persistence.dao.DAO;
import br.com.welson.clinic.persistence.model.ApplicationUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

import static br.com.welson.clinic.security.SecurityConstants.PREFIX;
import static br.com.welson.clinic.security.SecurityConstants.SECRET;

@RequestScoped
public class Authorization {

    @Inject
    private DAO<ApplicationUser> applicationUserDAO;

    public ApplicationUser getAuthorized(String token) {
        Claims body = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(PREFIX, "")).getBody();
        boolean expirationTimeIsValid = !body.getExpiration().before(Date.from(ZonedDateTime.now(ZoneOffset.UTC).toInstant()));
        if (!expirationTimeIsValid) {
            return null;
        }
        return getApplicationUserByUsername(body.getSubject());
    }

    private ApplicationUser getApplicationUserByUsername(String username) {
        List<ApplicationUser> applicationUsers = applicationUserDAO.findByHQLQuery(0, "applicationUserByUsername", username);
        return applicationUsers.size() == 1 ? applicationUsers.get(0) : null;
    }
}

package co.simplon.filrouge.security;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public abstract class JWTAuthenticationFilter extends GenericFilterBean {

    private String JWT_SECRET="super top secret";
    private String JWT_HEADER="Authorization";
    private String JWT_PREFIX="Bearer";

    protected Claims extractTokenClaims(ServletRequest request) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        Claims claims = null;
        String token = httpServletRequest.getHeader(JWT_HEADER);
        claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token.replace(JWT_PREFIX, ""))
                .getBody();

        return claims;
    }

    protected boolean checkTokenValidity(Claims claims){

        // Check the token expiration
        long expiration = claims.getExpiration().getTime();
        long notBefore = claims.getNotBefore().getTime();
        long now = System.currentTimeMillis();
        return (now > notBefore) && (expiration > now);

    }
}
package dev.ozkan.labreport.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthenticationProperties {
    private String cookieName = "accessToken";
    private String cookiePath = "/api/v1";

    @Value("${cookie.expiration}")
    private int cookieExpiration;

    public String getCookieName() {
        return cookieName;
    }

    public AuthenticationProperties setCookieName(String cookieName) {
        this.cookieName = cookieName;
        return this;
    }

    public String getCookiePath() {
        return cookiePath;
    }

    public AuthenticationProperties setCookiePath(String cookiePath) {
        this.cookiePath = cookiePath;
        return this;
    }

    public int getCookieExpiration() {
        return cookieExpiration;
    }

    public AuthenticationProperties setCookieExpiration(int cookieExpiration) {
        this.cookieExpiration = cookieExpiration;
        return this;
    }
}

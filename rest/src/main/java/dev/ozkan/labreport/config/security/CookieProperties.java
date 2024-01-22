package dev.ozkan.labreport.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CookieProperties {
    private String cookieName = "accessToken";
    private String cookiePath = "/api/v1";

    @Value("${cookie.expiration}")
    private int cookieExpiration;

    public String getCookieName() {
        return cookieName;
    }

    public CookieProperties setCookieName(String cookieName) {
        this.cookieName = cookieName;
        return this;
    }

    public String getCookiePath() {
        return cookiePath;
    }

    public CookieProperties setCookiePath(String cookiePath) {
        this.cookiePath = cookiePath;
        return this;
    }

    public int getCookieExpiration() {
        return cookieExpiration;
    }

    public CookieProperties setCookieExpiration(int cookieExpiration) {
        this.cookieExpiration = cookieExpiration;
        return this;
    }
}

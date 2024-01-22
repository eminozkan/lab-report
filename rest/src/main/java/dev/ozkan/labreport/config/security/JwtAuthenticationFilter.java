package dev.ozkan.labreport.config.security;

import dev.ozkan.labreport.model.session.UserSession;
import dev.ozkan.labreport.model.user.User;
import dev.ozkan.labreport.services.jwt.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;

    private final AuthenticationProperties authenticationProperties;

    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService, AuthenticationProperties authenticationProperties) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.authenticationProperties = authenticationProperties;
    }

    /**
     * Gelen her istek içerisinde bulunan, çerezden yetkilendirme için kullanılan accessToken isimli çerezi ayıklar ve değerini kontrol eder.
     * Geçerli bir çerez ise createSession() isimli methodu kullanarak bir oturum bilgisi oluşturur ve istek içerisine ekler.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = null;
        String username;

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(authenticationProperties.getCookieName())) {
                    token = cookie.getValue();
                }
            }
        }

        if (ObjectUtils.isEmpty(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        username = jwtService.extractUsername(token);
        if (username != null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtService.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                var session = createSession((User) userDetails);
                request.setAttribute("SESSION",session);
            }
        }

        filterChain.doFilter(request, response);
    }

    private static UserSession createSession(User userDetails) {
        var session = new UserSession();
        session.setUserId(userDetails.getUserId());
        session.setHospitalIdNumber(userDetails.getHospitalIdNumber());
        session.setUserRole(userDetails.getRole());
        return session;
    }
}

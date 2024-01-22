package dev.ozkan.labreport.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.ObjectUtils;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final AuthenticationProvider authenticationProvider;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final AuthenticationProperties properties;
    public SecurityConfig(AuthenticationProvider authenticationProvider, JwtAuthenticationFilter jwtAuthenticationFilter, AuthenticationProperties properties) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.properties = properties;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        request -> request
                                .requestMatchers("/register","/auth/login","/patients")
                                .permitAll()
                                .requestMatchers(HttpMethod.DELETE,"/reports/{reportId}")
                                .hasRole("MANAGER")
                                .requestMatchers("/users/**")
                                .hasRole("MANAGER")
                                .anyRequest()
                                .authenticated()
                )
                .logout((logout) -> logout
                        .logoutUrl("/auth/logout")
                        .invalidateHttpSession(true)
                        .deleteCookies(properties.getCookieName())
                        .logoutSuccessHandler((request, response, authentication) -> {
                            String referer = extractBaseUrl(request.getHeader("Referer"));
                            response.sendRedirect(referer);
                        })
                ).sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }

    private String extractBaseUrl(String referer) {
        int counter = 0;
        if (ObjectUtils.isEmpty(referer)){
            return "/";
        }

        for (int i = 0; i < referer.length(); i++) {
            if (referer.charAt(i) == '/'){
                counter++;
            }
            if (counter == 3){
                return referer.substring(0,i);
            }
        }
        return "/";
    }
}

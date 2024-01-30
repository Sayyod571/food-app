package org.example.cookieretceptg27.common.security;

import lombok.RequiredArgsConstructor;
import org.example.cookieretceptg27.common.jwt.JwtAuthenticationEntryPoint;
import org.example.cookieretceptg27.common.jwt.JwtSecurityFilter;
import org.example.cookieretceptg27.user.auth.CustomOauth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity(  prePostEnabled = true,
                        securedEnabled = true,
                        jsr250Enabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration
{
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;
    private final CustomOauth2UserService oauth2UserDetails;
    private final String[] WHITE_LIST = {
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/webjars/**",
            "/auth/***"
    };
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security, JwtSecurityFilter jwtSecurityFilter) throws Exception{

        return security
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(request -> request.requestMatchers(WHITE_LIST)
                        .permitAll()
                        .anyRequest()
                        .fullyAuthenticated())
                .addFilterBefore(jwtSecurityFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(entryPoint -> entryPoint.authenticationEntryPoint(authenticationEntryPoint))
                .oauth2Login(httpSecurityOAuth2LoginConfigurer -> httpSecurityOAuth2LoginConfigurer
                        .userInfoEndpoint(userInfoEndpointConfig
                                -> userInfoEndpointConfig.userService(oauth2UserDetails)))
                .formLogin(Customizer.withDefaults())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(8);
    }

}

package com.es.API_REST_Ez_Learning.security;

import com.es.API_REST_Ez_Learning.security.RsaKeyProperties;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private RsaKeyProperties rsaKeys;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/uploads/**").permitAll()
                                .requestMatchers("/usuarios/login", "/usuarios/register", "/usuarios/profesores").permitAll()
                                .requestMatchers(HttpMethod.GET, "/usuarios/{nombre}").authenticated()
                                .requestMatchers(HttpMethod.GET, "/usuarios/perfil-usuario").authenticated()
                                .requestMatchers(HttpMethod.GET, "/usuarios/{id}").authenticated()
                                .requestMatchers(HttpMethod.DELETE, "/usuarios/{id}").hasRole("PROFESOR")
                                .requestMatchers(HttpMethod.PUT, "/usuarios/{id}").authenticated()
                                .requestMatchers(HttpMethod.POST, "/tests/").hasRole("PROFESOR")
                                .requestMatchers(HttpMethod.GET, "/tests/").authenticated()
                                .requestMatchers(HttpMethod.GET, "/tests/{id}").authenticated()
                                .requestMatchers(HttpMethod.PUT, "/tests/{id}").hasRole("PROFESOR")
                                .requestMatchers(HttpMethod.DELETE, "/tests/{id}").hasRole("PROFESOR")
                                .requestMatchers("/preguntas").hasRole("PROFESOR")
                                .requestMatchers(HttpMethod.GET, "/puntuaciones/**").authenticated()
                                .requestMatchers(HttpMethod.DELETE, "/puntuaciones/{id}").hasRole("PROFESOR")
                                .requestMatchers(HttpMethod.GET, "/examenes/**").authenticated()
                                .anyRequest().authenticated()
                )
                .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults())
                .build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(rsaKeys.publicKey()).build();
    }

    /**
     * JWTENCODER codifica un token
     *
     * @return
     */
    @Bean
    public JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(rsaKeys.publicKey()).privateKey(rsaKeys.privateKey()).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }
}
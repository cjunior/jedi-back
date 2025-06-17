package com.ifce.jedi.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

//Tirar o default do SpringSecurity e fazer a minha config
@Configuration // declarando que é uma classe de configuração
@EnableWebSecurity // habilita o websecurity e eu que vou configurar
public class SecurityConfigurations {

    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(cors -> {})
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/"
                        ).permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").hasRole("ADMIN")

                        // Fluxo da pré-inscrição: tudo liberado
                        .requestMatchers(HttpMethod.POST, "/pre-inscricao/inicial").permitAll()
                        .requestMatchers(HttpMethod.GET, "/pre-inscricao/continuar/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/pre-inscricao/continuar/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/header/update").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/banner/slides/add").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/banner/slide/{slideId}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/banner/slide/{slideId}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/banner/update").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/header/get").permitAll()
                        .requestMatchers(HttpMethod.GET, "/banner/get").permitAll()
                        .requestMatchers(HttpMethod.GET, "/team/get").permitAll()
                        .requestMatchers(HttpMethod.POST, "/team/members/add").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/team/member/{memberId}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/team/member/{memberId}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/team/update").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/presentation-section/get").permitAll()
                        .requestMatchers(HttpMethod.POST, "/presentation-section/create").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/presentation-section/update").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/presentation-section/update-image").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "loadlandpage/get").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/update-all").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )

                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Encriptar a senha
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

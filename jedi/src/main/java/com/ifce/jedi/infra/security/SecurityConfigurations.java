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

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/"
                        ).permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").hasRole("ADMIN")

                        // Pré-inscrição
                        .requestMatchers(HttpMethod.POST, "/pre-inscricao/inicial").permitAll()
                        .requestMatchers(HttpMethod.GET, "/pre-inscricao/continuar/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/pre-inscricao/continuar/**").permitAll()

                        // Header e Banner
                        .requestMatchers(HttpMethod.PUT, "/header/update").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/header/get").permitAll()
                        .requestMatchers(HttpMethod.POST, "/banner/slides/add").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/banner/slide/{slideId}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/banner/slide/{slideId}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/banner/update").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/banner/get").permitAll()

                        // Equipe
                        .requestMatchers(HttpMethod.GET, "/team/get").permitAll()
                        .requestMatchers(HttpMethod.POST, "/team/members/add").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/team/member/{memberId}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/team/member/{memberId}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/team/update").hasRole("ADMIN")

                        // Sessão de apresentação
                        .requestMatchers(HttpMethod.GET, "/presentation-section/get").permitAll()
                        .requestMatchers(HttpMethod.POST, "/presentation-section/create").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/presentation-section/update").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/presentation-section/update-image").hasRole("ADMIN")

                        // Load Landpage
                        .requestMatchers(HttpMethod.GET, "loadlandpage/get").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/update-all").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/content/get").permitAll()
                        .requestMatchers(HttpMethod.POST, "/content/slides/add").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/content/update").hasRole("ADMIN")
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
        return new BCryptPasswordEncoder();
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

package com.ifce.jedi.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
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
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfigurations {

    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .cors(cors ->
                        cors.configurationSource(corsConfigurationSource()))
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
                        .requestMatchers(HttpMethod.POST, "/auth/forgot-password").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/auth/reset-password/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/auth/reset-password/**").permitAll()

                        .requestMatchers(HttpMethod.POST, "/management/register").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/management/pre-inscricoes").hasAnyRole("ADMIN", "GERENTE")
                        .requestMatchers(HttpMethod.GET, "/management/relatorio/pre-inscricoes/pdf").hasAnyRole("ADMIN", "GERENTE")
                        .requestMatchers(HttpMethod.GET, "/management/users").hasAnyRole("ADMIN", "GERENTE")
                        .requestMatchers(HttpMethod.GET, "/management/users/{id}").hasAnyRole("ADMIN", "GERENTE")
                        .requestMatchers(HttpMethod.PUT, "/management/users/**").hasAnyRole("ADMIN", "GERENTE")
                        .requestMatchers(HttpMethod.DELETE, "/management/users/**").hasRole("ADMIN")

                        // Pré-inscrição
                        .requestMatchers(HttpMethod.POST, "/pre-inscricao/inicial").permitAll()
                        .requestMatchers(HttpMethod.GET, "/pre-inscricao/continuar/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/pre-inscricao/continuar/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/pre-inscricoes/**").hasRole("ADMIN")

                        // Header e Banner
                        .requestMatchers(HttpMethod.PUT, "/header/update").hasAnyRole("ADMIN", "GERENTE")
                        .requestMatchers(HttpMethod.GET, "/header/get").permitAll()
                        .requestMatchers(HttpMethod.POST, "/banner/slides/add").hasAnyRole("ADMIN", "GERENTE")
                        .requestMatchers(HttpMethod.PUT, "/banner/slide/{slideId}").hasAnyRole("ADMIN", "GERENTE")
                        .requestMatchers(HttpMethod.DELETE, "/banner/slide/{slideId}").hasAnyRole("ADMIN", "GERENTE")
                        .requestMatchers(HttpMethod.PUT, "/banner/update").hasAnyRole("ADMIN", "GERENTE")
                        .requestMatchers(HttpMethod.GET, "/banner/get").permitAll()
                        .requestMatchers(HttpMethod.GET, "/publicos/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/sensiveis/**").permitAll()


                        // Equipe
                        .requestMatchers(HttpMethod.GET, "/team/get").permitAll()
                        .requestMatchers(HttpMethod.POST, "/team/members/add").hasAnyRole("ADMIN", "GERENTE")
                        .requestMatchers(HttpMethod.DELETE, "/team/member/{memberId}").hasAnyRole("ADMIN", "GERENTE")
                        .requestMatchers(HttpMethod.PUT, "/team/member/{memberId}").hasAnyRole("ADMIN", "GERENTE")
                        .requestMatchers(HttpMethod.PUT, "/team/update").hasAnyRole("ADMIN", "GERENTE")

                        // Sessão de apresentação
                        .requestMatchers(HttpMethod.GET, "/presentation-section/get").permitAll()
                        .requestMatchers(HttpMethod.POST, "/presentation-section/create").hasAnyRole("ADMIN", "GERENTE")
                        .requestMatchers(HttpMethod.PUT, "/presentation-section/update").hasAnyRole("ADMIN", "GERENTE")
                        .requestMatchers(HttpMethod.PUT, "/presentation-section/update-image").hasAnyRole("ADMIN", "GERENTE")

                        .requestMatchers(HttpMethod.GET, "/faq-section/get").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/faq-section/item/**").hasAnyRole("ADMIN", "GERENTE")
                        .requestMatchers(HttpMethod.PUT, "/faq-section/update-header").hasAnyRole("ADMIN", "GERENTE")
                        .requestMatchers(HttpMethod.POST, "/faq-section/items").hasAnyRole("ADMIN", "GERENTE")

                        // Load Landpage
                        .requestMatchers(HttpMethod.GET, "/loadlandpage/get").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/update-all").hasAnyRole("ADMIN", "GERENTE")

                        .requestMatchers(HttpMethod.GET, "/content/get").permitAll()
                        .requestMatchers(HttpMethod.POST, "/content/slides/add").hasAnyRole("ADMIN", "GERENTE")
                        .requestMatchers(HttpMethod.PUT, "/content/update").hasAnyRole("ADMIN", "GERENTE")
                        .requestMatchers(HttpMethod.PUT, "/content/slides/update").hasAnyRole("ADMIN", "GERENTE")

                        .requestMatchers(HttpMethod.POST, "/contact/email").permitAll()
                        .requestMatchers(HttpMethod.GET, "/contact/get").permitAll()
                        .requestMatchers(HttpMethod.PUT, "contact/update").hasAnyRole("ADMIN", "GERENTE")


                        .requestMatchers(HttpMethod.GET, "/blog-section/get").permitAll()
                        .requestMatchers(HttpMethod.POST, "/blog-section/item").hasAnyRole("ADMIN", "GERENTE", "BLOG")
                        .requestMatchers(HttpMethod.PUT, "/blog-section/item/{itemId}").hasAnyRole("ADMIN", "GERENTE", "BLOG")
                        .requestMatchers(HttpMethod.GET, "/blog-section/item/{itemId}").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/blog-section/item/{itemId}").hasAnyRole("ADMIN", "GERENTE", "BLOG")

                        .requestMatchers(HttpMethod.GET, "/rede-jedi").permitAll()
                        .requestMatchers(HttpMethod.POST, "/rede-jedi").hasAnyRole("ADMIN", "GERENTE")
                        .requestMatchers(HttpMethod.DELETE, "/rede-jedi/**").hasAnyRole("ADMIN", "GERENTE")
                        .requestMatchers(HttpMethod.PUT, "/rede-jedi/titulo").hasAnyRole("ADMIN", "GERENTE")

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

        // Use * se não tiver credenciais/cookies.
        // Se precisar de cookies/autenticação, coloque domínios específicos
        configuration.setAllowedOriginPatterns(List.of(
                "https://empreendedoresdigitais.ifce.edu.br",
                "https://jedi-front.vercel.app",
                "http://localhost:*",
                "https://*.ngrok.io",
                "https://*.ngrok-free.app"
        ));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

package com.sseprojet.sseprojet.config;

import com.sseprojet.sseprojet.security.AuthTokenFilter;
import com.sseprojet.sseprojet.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
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

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private AuthTokenFilter authTokenFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // Endpoints publics (authentification + documentation)
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                .requestMatchers("/api-docs/**").permitAll()
                
                // Endpoints d'administration (ADMINISTRATEUR uniquement)
                .requestMatchers("/api/users/**").hasRole("ADMINISTRATEUR")
                
                // === PROJETS - Configuration par méthode HTTP ===
                .requestMatchers(HttpMethod.GET, "/api/projets/**").hasAnyRole("CHEF_PROJET", "ADMINISTRATEUR", "DECIDEUR", "EVALUATEUR")
                .requestMatchers(HttpMethod.POST, "/api/projets/**").hasAnyRole("CHEF_PROJET", "ADMINISTRATEUR")
                .requestMatchers(HttpMethod.PUT, "/api/projets/**").hasAnyRole("CHEF_PROJET", "ADMINISTRATEUR")
                .requestMatchers(HttpMethod.PATCH, "/api/projets/**").hasAnyRole("CHEF_PROJET", "ADMINISTRATEUR")
                .requestMatchers(HttpMethod.DELETE, "/api/projets/**").hasAnyRole("CHEF_PROJET", "ADMINISTRATEUR")
                
                // === TÂCHES - Configuration par méthode HTTP ===
                .requestMatchers(HttpMethod.GET, "/api/taches/**").hasAnyRole("CHEF_PROJET", "EVALUATEUR", "ADMINISTRATEUR", "DECIDEUR")
                .requestMatchers(HttpMethod.POST, "/api/taches/**").hasAnyRole("CHEF_PROJET", "EVALUATEUR", "ADMINISTRATEUR")
                .requestMatchers(HttpMethod.PUT, "/api/taches/**").hasAnyRole("CHEF_PROJET", "EVALUATEUR", "ADMINISTRATEUR")
                .requestMatchers(HttpMethod.PATCH, "/api/taches/**").hasAnyRole("CHEF_PROJET", "EVALUATEUR", "ADMINISTRATEUR")
                .requestMatchers(HttpMethod.DELETE, "/api/taches/**").hasAnyRole("CHEF_PROJET", "ADMINISTRATEUR")
                
                // Endpoints de rapports (Tous les rôles authentifiés)
                .requestMatchers("/api/rapports/**").hasAnyRole("CHEF_PROJET", "EVALUATEUR", "DECIDEUR", "ADMINISTRATEUR")
                
                // Endpoints d'indicateurs (Tous les rôles authentifiés)
                .requestMatchers("/api/indicateurs/**").hasAnyRole("CHEF_PROJET", "EVALUATEUR", "DECIDEUR", "ADMINISTRATEUR")
                
                // Endpoints d'évaluateurs (CHEF_PROJET et ADMINISTRATEUR)
                .requestMatchers("/api/evaluateurs/**").hasAnyRole("CHEF_PROJET", "ADMINISTRATEUR")
                
                // Tous les autres endpoints nécessitent une authentification
                .anyRequest().authenticated()
            );

        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "https://votre-domaine-autorise.com"));
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With", "Accept"));
        configuration.setExposedHeaders(Arrays.asList("Authorization", "Content-Disposition"));
        configuration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

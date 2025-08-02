package com.sseprojet.sseprojet.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate6.Hibernate6Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Configuration Jackson pour gérer les proxies Hibernate et les dates Java 8+
 */
@Configuration
public class JacksonConfig {

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        
        // Ajouter le module Hibernate pour gérer les proxies
        Hibernate6Module hibernate6Module = new Hibernate6Module();
        hibernate6Module.disable(Hibernate6Module.Feature.USE_TRANSIENT_ANNOTATION);
        hibernate6Module.enable(Hibernate6Module.Feature.FORCE_LAZY_LOADING);
        hibernate6Module.enable(Hibernate6Module.Feature.SERIALIZE_IDENTIFIER_FOR_LAZY_NOT_LOADED_OBJECTS);
        
        mapper.registerModule(hibernate6Module);
        
        // Ajouter le module JavaTime pour gérer LocalDate, LocalDateTime, etc.
        mapper.registerModule(new JavaTimeModule());
        
        // Désactiver l'écriture des dates comme timestamps
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        
        return mapper;
    }
}

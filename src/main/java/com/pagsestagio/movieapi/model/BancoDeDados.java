package com.pagsestagio.movieapi.model;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class BancoDeDados {

    @Bean("bancoDados")
    public Map<Integer, String> getBancoDeDados() {
        return new HashMap<>();
    }

}

package com.pagsestagio.movieapi.oldversion.model;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Deprecated
@Configuration
public class BancoDeDadosOldVersion {

    @Bean("bancoDados")
    public Map<Integer, String> getBancoDeDados() {
        return new HashMap<>();
    }

}

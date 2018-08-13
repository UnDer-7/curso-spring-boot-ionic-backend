package com.matus.cursomc.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matus.cursomc.domain.PagamentoComBoleto;
import com.matus.cursomc.domain.PagamentoComCartao;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

public class JacksonConfig {

    // https://stackoverflow.com/questions/41452598/overcome-can-not-construct-instance-ofinterfaceclass-without-hinting-the-pare

    // Serve para configurar SubClasses, classes abstratas (q não podem ser instaciadas)
    @Bean
    public Jackson2ObjectMapperBuilder objectMapperBuilder() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder() {
            public void configure(ObjectMapper objectMapper) {
                //Unico coisa q mudo é o registerSubtypes
                objectMapper.registerSubtypes(PagamentoComCartao.class);
                objectMapper.registerSubtypes(PagamentoComBoleto.class);
                super.configure(objectMapper);
            }
        };
        return builder;
    }
}

package com.menezes.neto.dreamshops.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShopConfig {

    @Bean
    public ModelMapper modelMapper(){
        return new MoldeMapper();
    }
}

package com.nttdata.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import org.modelmapper.ModelMapper;

@ApplicationScoped
public class BeanConfig {

    @Produces
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}


package com.financialapp.config;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;

public class ModelMapperConfigTest {

    @Test
    public void testModelMapperCreation() {
        ModelMapperConfig config = new ModelMapperConfig();
        ModelMapper modelMapper = config.modelMapper();
        
        assertNotNull(modelMapper);
        assertInstanceOf(ModelMapper.class, modelMapper);
        
        // Test that the ModelMapper is functional
        assertNotNull(modelMapper.getConfiguration());
    }
}
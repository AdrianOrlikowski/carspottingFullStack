package pl.orlikowski.carspottingBack.beans;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//This class is meant to initialize various small beans used throughout the application
@Configuration
public class Beans {
    @Bean
    ModelMapper modelMapper() { return new ModelMapper();}
}

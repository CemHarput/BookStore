package com.example.bookStore;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication()
public class BookStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookStoreApplication.class, args);
	}
	@Bean
	public ModelMapper modelMapper(){ return new ModelMapper();	}
	@Bean
	public OpenAPI customOpenAPI(){
		return new OpenAPI().info(new Info());

	}
}

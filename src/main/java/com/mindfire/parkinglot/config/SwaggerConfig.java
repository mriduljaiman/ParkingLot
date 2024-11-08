package com.mindfire.parkinglot.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {
	
	@Bean
	public OpenAPI customOpenAPI() {
		
		return new OpenAPI()
				.info(new Info().title("parking lot"))				
				.addSecurityItem(new SecurityRequirement().addList("mindfire"))
				.components(new Components().addSecuritySchemes("mindfire", new SecurityScheme()
						.name("mindfire").type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
		
	}
}


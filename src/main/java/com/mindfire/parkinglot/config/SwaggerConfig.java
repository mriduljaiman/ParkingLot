package com.mindfire.parkinglot.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mindfire.parkinglot.constant.Constants;

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
				.info(new Info().title(Constants.API_TITLE))				
				.addSecurityItem(new SecurityRequirement().addList(Constants.SECURITY_SCHEME_NAME))
				.components(new Components().addSecuritySchemes(Constants.SECURITY_SCHEME_NAME, new SecurityScheme()
						.name(Constants.SECURITY_SCHEME_NAME).type(SecurityScheme.Type.HTTP).scheme(Constants.SECURITY_SCHEME_TYPE).bearerFormat(Constants.BEARER_FORMAT)));
		
	}
}


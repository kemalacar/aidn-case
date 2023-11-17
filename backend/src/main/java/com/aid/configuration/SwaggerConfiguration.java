package com.aid.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.AuthorizationScopeBuilder;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;

import static com.google.common.collect.Lists.newArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration extends WebMvcConfigurationSupport {

	@Bean
	public Docket appApi() {
		AuthorizationScope[] authScopes = new AuthorizationScope[1];
		authScopes[0] = new AuthorizationScopeBuilder().scope("global").description("full access").build();
		SecurityReference securityReference = SecurityReference.builder().reference("Authorization")
				.scopes(authScopes).build();

		ArrayList<SecurityContext> securityContexts = newArrayList(
				SecurityContext.builder().securityReferences(newArrayList(securityReference)).build());
		return new Docket(DocumentationType.SWAGGER_2)
				.securitySchemes(newArrayList(new ApiKey("Authorization", "Authorization", "header")))
				.securityContexts(securityContexts).apiInfo(apiInfo()).select().build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfo("SPRING BOOT DEMO", "REST API", "1.0.0", "https://www.my-company.com",
				new Contact("IT Team", "https://www.my-company.com", "xx@xx.xom"), "TECH",
				"https://www.my-company.com", Collections.emptyList());
	}

	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");

		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
}

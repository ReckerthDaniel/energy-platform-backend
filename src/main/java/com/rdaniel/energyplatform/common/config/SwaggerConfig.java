package com.rdaniel.energyplatform.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    public static final String APP_USER_TAG = "API AppUser Controller";
    public static final String DEVICE_TAG = "API Device Controller";
    public static final String MEASUREMENT_TAG = "API Measurement Controller";

    public static final String APP_USER_DESCRIPTION = "AppUser Controller Resource";
    public static final String DEVICE_DESCRIPTION = "Device Controller Resource";
    public static final String MEASUREMENT_DESCRIPTION = "Measurement Controller Resource";

    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.rdaniel.energyplatform"))
                .paths(PathSelectors.any())
                .build()
                .tags(  new Tag(APP_USER_TAG, APP_USER_DESCRIPTION),
                        new Tag(DEVICE_TAG, DEVICE_DESCRIPTION),
                        new Tag(MEASUREMENT_TAG, MEASUREMENT_DESCRIPTION)
                )
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Energy Platform API Documentation")
                .version("1.0")
                .description("Documentation for energy platform API endpoints")
                .build();
    }
}

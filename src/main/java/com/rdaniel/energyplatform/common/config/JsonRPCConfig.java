package com.rdaniel.energyplatform.common.config;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImplExporter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class JsonRPCConfig implements WebMvcConfigurer {

    @Bean
    public static AutoJsonRpcServiceImplExporter autoJsonRpcServiceImplExporter() {
        return new AutoJsonRpcServiceImplExporter();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/rpc").allowedOrigins("*");
    }
}

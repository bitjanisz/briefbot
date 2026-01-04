package com.admeliora.briefbot;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // przekierowanie / na index.html (jeśli jest w static)
        registry.addViewController("/").setViewName("forward:/index.html");
        registry.addViewController("/other/**").setViewName("forward:/index.html");
        registry.addViewController("/login/**").setViewName("forward:/index.html");
    }
}

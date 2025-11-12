package com.bunary.vocab.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Map tất cả URL /resources/** tới thư mục static
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("classpath:/static/");
    }
}

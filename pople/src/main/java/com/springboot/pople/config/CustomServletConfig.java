package com.springboot.pople.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CustomServletConfig  implements WebMvcConfigurer {
    @Value("${uploadPath}")
    String uploadPath;
    @Value("${boarduploadPath}")
    String uploadPath2;

    @Value("${itemuploadPath}")
    String uploadPath3;



    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("/fonts/**")
                .addResourceLocations("classpath:/static/fonts/");
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/assets/**").
                addResourceLocations("classpath:/static/assets/");
        registry.addResourceHandler("/bootstrap/**").
                addResourceLocations("classpath:/static/bootstrap/");
        registry.addResourceHandler("/images/**")
                .addResourceLocations(uploadPath);
        registry.addResourceHandler("/images2/**/")
                .addResourceLocations(uploadPath2);
        registry.addResourceHandler("/images3/**/")
                .addResourceLocations(uploadPath3);

    }
}

package project.SangHyun.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${spring.domain.local}")
    private String allowedOrigin1;

    @Value("${spring.domain.prod}")
    private String allowedOrigin2;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(allowedOrigin1)
                .allowCredentials(true)
                .allowedMethods("*")
                .allowedHeaders("*");

        registry.addMapping("/**")
                .allowedOrigins(allowedOrigin2)
                .allowCredentials(true)
                .allowedMethods("*")
                .allowedHeaders("*");
    }
}

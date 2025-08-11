package github.williamjbf.primeiraaventura.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")               // Permite CORS para todas rotas
                        .allowedOrigins("http://localhost:3000")  // Origem do seu frontend (ajuste conforme seu domínio)
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos permitidos
                        .allowedHeaders("*")                 // Permite todos os headers
                        .allowCredentials(true);             // Permite envio de cookies/credentials
            }
        };
    }

}

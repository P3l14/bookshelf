package de.workshops.bookshelf;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(("test"))
public class SwaggerConfiguration {


    @Bean
    public OpenAPI api(@Value("${bookshelf.title}") String title, @Value("${bookshelf.version}") String version) {
        return new OpenAPI()
                .info(
                        new Info()
                                .title(title +" API")
                                .version("v"+ version)
                                .license(new License()
                                        .name("MIT License")
                                        .url("https://opensource.org/licenses/MIT")
                                )
                );
    }
}

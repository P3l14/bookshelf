package de.workshops.bookshelf;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class SwaggerConfiguration {


    @Bean
    public OpenAPI api(BookshelfProperties bookshelfProperties) {
        OpenAPI openAPI = new OpenAPI()
                .info(
                        new Info()
                                .title(bookshelfProperties.getTitle() + " API")
                                .version("v" + bookshelfProperties.getVersion())
                                .description("Gurantees a capacity of " + bookshelfProperties.getCapacity())
                                .license(new License()
                                        .name("MIT License")
                                        .url("https://opensource.org/licenses/MIT")
                                )
                );
        return openAPI;
    }
}

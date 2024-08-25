package com.david.mbaimbai.farmcollector;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Crop Api",
				description = "API for Creating, getting, updating and deleting crops from FarmTracker",
				version = "v1",
				summary = "Creating, getting, updating and deleting Apis",
				contact = @Contact(
						name = "David Mbaimbai",
						url = "https://www.david.mbaimbai.co.za",
						email = "davymbaimbai@gmail.com"
				)
		),
		externalDocs = @ExternalDocumentation(url = "https://www.david.mbaimbai.co.za/")
)
public class FarmCollectorApplication {

	public static void main(String[] args) {
		SpringApplication.run(FarmCollectorApplication.class, args);
	}

}

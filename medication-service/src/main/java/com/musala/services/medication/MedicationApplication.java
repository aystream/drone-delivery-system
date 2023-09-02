package com.musala.services.medication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(title = "Medication API", version = "1.0", description = "Documentation Medication API v1.0")
)
public class MedicationApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedicationApplication.class, args);
    }

}

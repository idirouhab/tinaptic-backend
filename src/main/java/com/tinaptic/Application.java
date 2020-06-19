package com.tinaptic;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "tinaptic",
                version = "1.0",
                contact = @Contact(url = "https://tinaptic.com", name = "Idir", email = "iouhab@tinaptic.com")
        )
)
public class Application {

  public static void main(String[] args) {
    Micronaut.run(Application.class);
  }
}

package com.tinaptic.controllers;

import com.tinaptic.services.UserService;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;

@Controller("/something")
class LoginController {
  @Get(value = "/", produces = MediaType.APPLICATION_JSON)
  @Secured(SecurityRule.IS_ANONYMOUS)
  HttpStatus list(@QueryValue String username, @QueryValue String password) {

    System.out.println(username);
    System.out.println(password);

    return HttpStatus.OK;
  };
}

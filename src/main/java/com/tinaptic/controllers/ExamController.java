package com.tinaptic.controllers;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;

import java.security.Principal;

@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller("/api/exam")
class ExamController {
  @Get(value = "/", produces = MediaType.TEXT_PLAIN)
  public String list(Principal principal) {
    return principal.getName();
  };
}

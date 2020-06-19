package com.tinaptic.dtos;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Introspected
public class UserDto {

  @NotBlank private String id;

  @NotBlank private String username;

  @NotBlank private String password;

  @NotBlank private String name;

  @NotBlank private String lastName;

  @NotBlank private Boolean isVerified;

  @NotBlank private Boolean isBlocked;

  private String role;
}

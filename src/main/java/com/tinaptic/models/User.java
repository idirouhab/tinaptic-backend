package com.tinaptic.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User {
  public static final String DEFAULT_ROLE = "ADMIN";

  private String _id;
  private String username;
  private String password;
  private String name;
  private String lastName;
  private Boolean isVerified;
  private Boolean isBlocked;

  @Builder.Default private String role = DEFAULT_ROLE;

  private String token;
}

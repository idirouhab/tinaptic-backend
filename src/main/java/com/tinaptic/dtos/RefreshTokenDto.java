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
public class RefreshTokenDto {

  @NotBlank private String refreshToken;

  @NotBlank private String userId;
}

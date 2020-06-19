package com.tinaptic.mappers;

import com.tinaptic.dtos.RefreshTokenDto;
import com.tinaptic.models.RefreshToken;

import javax.inject.Singleton;

@Singleton
public class RefreshTokenMapper {
  public RefreshToken toEntity(RefreshTokenDto refreshTokenDto) {
    return RefreshToken.builder()
        .refreshToken(refreshTokenDto.getRefreshToken())
        .userId(refreshTokenDto.getUserId())
        .build();
  }

  public RefreshTokenDto toDTO(RefreshToken refreshToken) {
    return RefreshTokenDto.builder()
        .refreshToken(refreshToken.getRefreshToken())
        .userId(refreshToken.getUserId())
        .build();
  }

  public RefreshToken toEntity(String userId, String refreshToken) {
    return RefreshToken.builder().refreshToken(refreshToken).userId(userId).build();
  }
}

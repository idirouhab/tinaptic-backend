package com.tinaptic.services;

import com.tinaptic.mappers.RefreshTokenMapper;
import com.tinaptic.repositories.RefreshTokenRepository;
import com.tinaptic.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;

@Slf4j
@Singleton
public class RefreshTokenService {
  private final RefreshTokenRepository refreshTokenRepository;

  private final RefreshTokenMapper refreshTokenMapper;
  private final UserRepository userRepository;

  public RefreshTokenService(
      RefreshTokenRepository refreshTokenRepository,
      RefreshTokenMapper refreshTokenMapper,
      UserRepository userRepository) {
    this.refreshTokenRepository = refreshTokenRepository;
    this.refreshTokenMapper = refreshTokenMapper;
    this.userRepository = userRepository;
  }

  public void saveRefreshToken(String username, String refreshToken) {
    userRepository
        .findByUsername(username)
        .ifPresent(
            (user) ->
                refreshTokenRepository.save(
                    refreshTokenMapper.toEntity(user.get_id(), refreshToken)));
  }
}

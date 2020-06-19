package com.tinaptic.services;

import com.tinaptic.dtos.UserDto;
import com.tinaptic.mappers.UserMapper;
import com.tinaptic.repositories.RefreshTokenRepository;
import com.tinaptic.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
import java.util.Optional;

@Slf4j
@Singleton
public class UserService {
  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final RefreshTokenRepository refreshTokenRepository;

  public UserService(
      UserRepository userRepository,
      UserMapper userMapper,
      RefreshTokenRepository refreshTokenRepository) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
    this.refreshTokenRepository = refreshTokenRepository;
  }

  public Optional<UserDto> findByRefreshToken(String refreshToken) {
    return refreshTokenRepository
        .findByRefreshToken(refreshToken)
        .flatMap(rT -> userRepository.findById(rT.getUserId()).map(userMapper::toDTO));
  }

  public Optional<UserDto> findUser(String username) {
    return userRepository.findByUsername(username).map(userMapper::toDTO);
  }
}

package com.tinaptic.mappers;

import com.tinaptic.dtos.UserDto;
import com.tinaptic.models.User;

import javax.inject.Singleton;

@Singleton
public class UserMapper {
  public User toEntity(UserDto userDto) {
    return User.builder()
        ._id(userDto.getId())
        .username(userDto.getUsername())
        .password(userDto.getPassword())
        .lastName(userDto.getLastName())
        .name(userDto.getName())
        .isBlocked(userDto.getIsBlocked())
        .isVerified(userDto.getIsVerified())
        .role(userDto.getRole())
        .build();
  }

  public UserDto toDTO(User user) {
    return UserDto.builder()
        .id(user.get_id())
        .username(user.getUsername())
        .password(user.getPassword())
        .lastName(user.getLastName())
        .name(user.getName())
        .isBlocked(user.getIsBlocked())
        .isVerified(user.getIsVerified())
        .role(user.getRole())
        .build();
  }
}

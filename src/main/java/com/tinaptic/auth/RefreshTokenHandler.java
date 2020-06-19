package com.tinaptic.auth;

import com.tinaptic.services.RefreshTokenService;
import com.tinaptic.services.UserService;
import io.micronaut.runtime.event.annotation.EventListener;
import io.micronaut.security.authentication.UserDetails;
import io.micronaut.security.token.event.RefreshTokenGeneratedEvent;
import io.micronaut.security.token.refresh.RefreshTokenPersistence;
import io.reactivex.Flowable;
import org.reactivestreams.Publisher;

import java.util.Collections;

public class RefreshTokenHandler implements RefreshTokenPersistence {

  private final RefreshTokenService refreshTokenService;
  private final UserService userService;

  public RefreshTokenHandler(RefreshTokenService refreshTokenService, UserService userService) {
    this.refreshTokenService = refreshTokenService;
    this.userService = userService;
  }

  @Override
  @EventListener
  public void persistToken(RefreshTokenGeneratedEvent event) {
    System.out.println(event.getUserDetails().getUsername());

    refreshTokenService.saveRefreshToken(
        event.getUserDetails().getUsername(), event.getRefreshToken());
  }

  @Override
  public Publisher<UserDetails> getUserDetails(String refreshToken) {
    System.out.println(refreshToken);

    return userService
        .findByRefreshToken(refreshToken)
        .map(
            user ->
                Flowable.just(
                    new UserDetails(user.getUsername(), Collections.singletonList(user.getRole()))))
        .orElse(Flowable.empty());
  }
}

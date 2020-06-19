package com.tinaptic.auth;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.tinaptic.dtos.UserDto;
import com.tinaptic.services.UserService;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.*;
import io.reactivex.Flowable;
import lombok.SneakyThrows;
import org.reactivestreams.Publisher;

import javax.inject.Singleton;
import java.security.SecureRandom;
import java.util.Optional;

import static io.micronaut.security.authentication.AuthenticationFailureReason.PASSWORD_EXPIRED;
import static io.micronaut.security.authentication.AuthenticationFailureReason.USER_NOT_FOUND;
import static java.util.Collections.singletonList;

@Singleton
public class BasicAuthProvider implements AuthenticationProvider {

  private final UserService userService;
  private static final SecureRandom RAND = new SecureRandom();

  public BasicAuthProvider(UserService userService) {
    this.userService = userService;
  }

  @SneakyThrows
  @Override
  public Publisher<AuthenticationResponse> authenticate(
      HttpRequest httpReq, AuthenticationRequest authReq) {

    final String username = authReq.getIdentity().toString();
    final String password = authReq.getSecret().toString();

    Optional<UserDto> existingUser = userService.findUser(username);

    return Flowable.just(
        existingUser
            .map(
                user -> {
                  String bcryptHashString =
                      BCrypt.withDefaults().hashToString(12, password.toCharArray());
                  if (BCrypt.verifyer().verify(password.toCharArray(), bcryptHashString).verified) {
                    return new UserDetails(username, singletonList(user.getRole()));
                  }
                  return new AuthenticationFailed(PASSWORD_EXPIRED);
                })
            .orElse(new AuthenticationFailed(USER_NOT_FOUND)));
  }
}

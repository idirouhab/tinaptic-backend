package com.tinaptic.auth

import io.micronaut.context.ApplicationContext
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.security.authentication.UsernamePasswordCredentials
import io.micronaut.security.token.jwt.render.BearerAccessRefreshToken
import io.micronaut.test.annotation.MicronautTest
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification


@MicronautTest
class LoginAuthenticationSpec extends Specification {

    @Shared
    @AutoCleanup
    EmbeddedServer embeddedServer = ApplicationContext.run(EmbeddedServer)


    @Shared
    @AutoCleanup
    RxHttpClient client = embeddedServer.applicationContext.createBean(RxHttpClient, embeddedServer.getURL())



    def "Try to login with an existing user"() {
        given: 'User data'
        String email = 'ouhab.idir@gmail.com'
        String password = 'klis9yull-lenk_DRAR'

        when: 'Login endpoing is called with an known email'
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(email, password)
        HttpRequest request = HttpRequest.POST('/login', credentials)
        HttpResponse<BearerAccessRefreshToken> response = client.toBlocking().exchange(request, BearerAccessRefreshToken)

        then: 'return 200'
        response.status == HttpStatus.OK

    }

    def "Try to login with a non existing user and fail"() {
        given: 'User data'
        String email = 'non-existing'
        String password = 'random-pass'

        when: 'Login endpoint is called with an unknown email'
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(email, password)
        HttpRequest request = HttpRequest.POST('/login', credentials)
        HttpResponse<BearerAccessRefreshToken> response = client.toBlocking().exchange(request, BearerAccessRefreshToken)

        then: 'returns unauthorized'
        !response
        HttpClientResponseException e = thrown(HttpClientResponseException)
        e.status == HttpStatus.UNAUTHORIZED
    }
}

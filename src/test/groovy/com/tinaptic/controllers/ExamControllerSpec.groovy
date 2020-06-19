package com.tinaptic.controllers

import com.nimbusds.jwt.JWTParser
import com.nimbusds.jwt.SignedJWT
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
class ExamControllerSpec extends Specification {
    @Shared
    @AutoCleanup
    EmbeddedServer embeddedServer = ApplicationContext.run(EmbeddedServer)

    @Shared
    @AutoCleanup
    RxHttpClient client = embeddedServer.applicationContext.createBean(RxHttpClient, embeddedServer.getURL())

    def 'Accessing a secured URL without authenticating return unauthorized'() {
        when:
        HttpRequest request = HttpRequest.GET('/api/exam')
        HttpResponse<BearerAccessRefreshToken> response = client.toBlocking().exchange(request, BearerAccessRefreshToken)
        then:
        HttpClientResponseException e = thrown()
        e.status == HttpStatus.UNAUTHORIZED
    }

    def "upon successful authentication, a Json Web token is issued to the user"() {
        given: 'User data'
        String email = 'ouhab.idir@gmail.com'
        String password = 'klis9yull-lenk_DRAR'

        when: 'Login endpoint is called with valid credentials'
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(email, password)
        HttpRequest request = HttpRequest.POST('/login', credentials)
        BearerAccessRefreshToken rsp = client.toBlocking().retrieve(request, BearerAccessRefreshToken)
        then: 'the endpoint can be accessed'
        rsp.username == email
        rsp.accessToken
        rsp.refreshToken

        when:
        BearerAccessRefreshToken bearerAccessRefreshToken = rsp.body()

        then:
        bearerAccessRefreshToken.username == email
        bearerAccessRefreshToken.accessToken

        and: 'the access token is a signed JWT'
        JWTParser.parse(bearerAccessRefreshToken.accessToken) instanceof SignedJWT

        when: 'passing the access token as in the Authorization HTTP Header with the prefix Bearer allows the user to access a secured endpoint'
        String accessToken = bearerAccessRefreshToken.accessToken
        HttpRequest requestWithAuthorization = HttpRequest.GET('/api/exam')
                .bearerAuth(accessToken)
        HttpResponse<String> response = client.toBlocking().exchange(requestWithAuthorization, String)

        then:
        response.status == HttpStatus.OK
        response.body() == email
    }
}

package example.com.plugins

import example.authenticate
import example.com.data.user.MongoUserDataSource
import example.com.security.Hashing.SHA256HashingService
import example.com.security.token.JwtTokenService
import example.com.security.token.TokenConfig
import example.getSecretInfo
import example.signIn
import example.signUp
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting(
    userDataSource: MongoUserDataSource,
    tokenService: JwtTokenService,
    hashingService: SHA256HashingService,
    tokenConfig: TokenConfig
) {
    routing {
        //Todo
        signIn(
            userDataSource, hashingService, tokenService, tokenConfig
        )

        signUp(
            hashingService, userDataSource
        )

        authenticate()
        getSecretInfo()

    }
}

package example

import ch.qos.logback.core.subst.Token
import example.com.data.requests.AuthRequest
import example.com.data.user.User
import example.com.data.user.UserDataSource
import example.com.security.Hashing.HashingService
import example.com.security.Hashing.SaltedHash
import example.com.security.token.TokenClaim
import example.com.security.token.TokenConfig
import example.com.security.token.TokenService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.signUp(
    hashingService: HashingService,
    userDataSource: UserDataSource,

    ) {
    post("signup") {
        val request = call.receiveOrNull<AuthRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val areFieldsBlank = request.username.isBlank() || request.password.isBlank()
        val isPwTooShort = request.password.length < 8 || request.password.isBlank()
        if (areFieldsBlank || isPwTooShort) {
            call.respond(
                HttpStatusCode.Conflict,
                "not proper"
            )
            return@post
        }

        val saltedHash = hashingService.generateSaltedHash(request.password)
        val user = User(
            username = request.username,
            password = request.password,
            salt = saltedHash.salt
        )
        val wasAcknowledged = userDataSource.insertUser(user)
        if (! wasAcknowledged) {
            call.respond(HttpStatusCode.Conflict)
            return@post
        }

        call.respond(HttpStatusCode.OK)
    }
}

fun Route.signIn(
    userDataSource: UserDataSource,
    hashingService: HashingService,
    tokenService: TokenService,
    tokenConfig: TokenConfig
) {
    post("signIn") {
        val request = call.receiveOrNull<AuthRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val user = userDataSource.getUserByUsername(request.username) ?: kotlin.run {
            call.respond(HttpStatusCode.NotFound)
            return@post
        }

        if (user == null) {
            call.respond(HttpStatusCode.NotFound)
        }

        val isValidPassword = hashingService.verify(
            value = request.password,
            saltedHash = SaltedHash(
                user.password,
                user.salt

            )
        )
        if (!isValidPassword) {
            call.respond(HttpStatusCode.Conflict,"Incorrect password or username")
            return@post
        }
        val token = tokenService.generateToken(
            config = tokenConfig,
            TokenClaim(
                name = "User Id",
                value = user.id
            )
        )

    }

}
package example.com

import example.com.data.user.MongoUserDataSource
import example.com.data.user.User
import example.com.plugins.*
import example.com.security.Hashing.SHA256HashingService
import example.com.security.token.JwtTokenService
import example.com.security.token.TokenConfig
import io.ktor.server.application.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

@OptIn(DelicateCoroutinesApi::class)
fun Application.module() {
//    val mongoPW = System.getenv("MONGO_PW")
    val mongoPW = System.getenv("MONGO_PW") ?: throw IllegalStateException("MONGO_PW not set")

    val dbName = "ktor-auth"
    val db =
        KMongo.createClient(
            connectionString = "mongodb+srv://pratiksukaledev:$mongoPW@cluster0.hwze1s7.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0"
        ).coroutine
            .getDatabase(dbName)


    val userDataSource = MongoUserDataSource(db)
    val tokenService = JwtTokenService()
    val tokenConfig = TokenConfig(
        issuer = environment.config.property("jwt.issuer").getString(),
        audience = environment.config.property("jwt.audience").getString(),
        expiresIn = 365L * 1000L * 60L * 60L * 24L,
        secret = System.getenv("JWT_SECRET")
    )

    val hashingService = SHA256HashingService()

    configureRouting()
    configureSerialization()
//    configureDatabases()
    configureMonitoring()
    configureSecurity(config = tokenConfig)

}

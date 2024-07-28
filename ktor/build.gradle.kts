
val kotlin_version: String by project
val logback_version: String by project
val mongo_version: String by project
val ktor_version : String by project


plugins {
    kotlin("jvm") version "2.0.0"
    id("io.ktor.plugin") version "2.3.12"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.0"
}

group = "example.com"
version = "0.0.1"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")

    implementation("io.ktor:ktor-server-call-logging-jvm:$ktor_version")

    implementation("org.mongodb:mongodb-driver-core:$mongo_version")
    implementation("org.mongodb:mongodb-driver-sync:$mongo_version")
    implementation("org.mongodb:bson:$mongo_version")
    implementation("io.ktor:ktor-server-auth-jvm")

    implementation("io.ktor:ktor-server-auth-jwt-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")

    implementation("io.ktor:ktor-server-auth")
    implementation("io.ktor:ktor-server-auth-jwt")


    implementation("ch.qos.logback:logback-classic:$logback_version")
    testImplementation("io.ktor:ktor-server-test-host-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")


    implementation("org.litote.kmongo:kmongo-coroutine-serialization:4.10.0")
    implementation("org.litote.kmongo:kmongo-coroutine-core:4.10.0")
    implementation("org.mongodb:mongodb-driver-reactivestreams:4.10.0")



    implementation("io.insert-koin:koin-core:3.1.2")
    implementation("io.insert-koin:koin-ktor:3.1.2")
    implementation("io.insert-koin:koin-logger-slf4j:3.1.2")

    implementation("commons-codec:commons-codec:1.15")

}

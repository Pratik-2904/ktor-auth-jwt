package example.com.security.Hashing

data class SaltedHash(
    val hash: String,
    val salt: String
)
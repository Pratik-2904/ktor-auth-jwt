package example.com.data

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val token:String,

)
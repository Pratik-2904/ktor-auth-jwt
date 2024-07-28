package example.com.security.token

import example.com.data.user.User

interface TokenService {

    fun generateToken(
        config: TokenConfig,
        vararg claims: TokenClaim
    ) : String

}
package example.com.data.user

interface UserDataSource {
    suspend fun getUserByUsername(username: String): User?

    suspend fun insertUser(user: User): Boolean


}
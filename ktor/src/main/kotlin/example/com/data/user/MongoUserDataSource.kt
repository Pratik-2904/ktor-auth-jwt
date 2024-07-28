package example.com.data.user

import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class MongoUserDataSource(
    db: CoroutineDatabase,

    ) : UserDataSource {
    private val users = db.getCollection<User>()

    //    ctrl+I
    override suspend fun getUserByUsername(username: String): User? {
        return users.findOne(
            filter = User::username eq username
        )
    }

    override suspend fun insertUser(user: User): Boolean {
        return users.insertOne(user).wasAcknowledged()
    }

}
package space.yaroslav.familybot.repos.ifaces

import space.yaroslav.familybot.common.Chat
import space.yaroslav.familybot.common.Pidor
import space.yaroslav.familybot.common.User
import java.time.Instant


interface CommonRepository {

    fun addUser(user: User)

    fun getUsers(chat: Chat): List<User>

    fun addChat(chat: Chat)

    fun getChats(): List<Chat>

    fun addPidor(pidor: Pidor)

    fun getPidorsByChat(chat: Chat, startDate: Instant = Instant.ofEpochMilli(969652800000),
                        endDate: Instant = Instant.now()): List<Pidor>

    fun containsUser(user: User): Boolean

    fun containsChat(chat: Chat): Boolean
}
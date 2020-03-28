package space.yaroslav.familybot.route.services.state

import space.yaroslav.familybot.route.services.state.exhibits.FunctionalToleranceState
import space.yaroslav.familybot.route.services.state.exhibits.State
import kotlin.reflect.KClass

interface StateService {

    fun setStateForChat(chatId: Long, state: State)
    fun setStateForUser(userId: Long, state: State)
    fun setStateForUserAndChat(userId: Long, chatId: Long, state: State)

    fun <T : State> getStateForChat(chatId: Long, requiredType: KClass<T>): T?
    fun <T : State> getStateForUser(userId: Long, requiredType: KClass<T>): T?
    fun <T : State> getStateForUserAndChat(chatId: Long, userId: Long, requiredType: KClass<T>): T?
    fun <T : State> getAllStatesByChatPerUser(chatId: Long, requiredType: KClass<T>): Map<Long, T>

    fun getFunctionToleranceStatesForChat(chatId: Long): Set<FunctionalToleranceState>
}

data class StateKey(val chatId: Long? = null, val userId: Long? = null)


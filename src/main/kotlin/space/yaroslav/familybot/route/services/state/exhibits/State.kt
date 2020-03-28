package space.yaroslav.familybot.route.services.state.exhibits

import space.yaroslav.familybot.route.models.StateType

interface State : AutoCloseable {

    val id: Long?

    fun checkIsItOverAlready(): Boolean

    fun type(): StateType

    override fun close() {}
}

package space.yaroslav.familybot.repos.ifaces

import space.yaroslav.familybot.route.services.state.StateKey
import space.yaroslav.familybot.route.services.state.exhibits.State

interface StateRepository {

    fun addState(stateKey: StateKey, state: State)

    fun getState(stateKey: StateKey): List<State>


}
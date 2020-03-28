package space.yaroslav.familybot.route.models

import space.yaroslav.familybot.route.services.state.exhibits.FuckOffState
import space.yaroslav.familybot.route.services.state.exhibits.FuckOffToleranceState
import space.yaroslav.familybot.route.services.state.exhibits.RageModeState
import space.yaroslav.familybot.route.services.state.exhibits.State
import kotlin.reflect.KClass

enum class StateType(val id: Int, val clazz: KClass<out State>) {

    FUCK_OFF(1, FuckOffState::class),
    FUCK_OFF_TOLERANCE(2, FuckOffToleranceState::class),
    RAGE_MODE(3, RageModeState::class);

    companion object {
        private val lookup = values().associateBy(StateType::id)
        fun findById(id: Int): StateType? {
            return lookup[id]
        }
    }
}

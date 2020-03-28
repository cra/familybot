package space.yaroslav.familybot.route.services.state.exhibits

import space.yaroslav.familybot.route.models.StateType
import java.time.Duration

class RageModeState(var amountOfMessagesToBeRaged: Int, duration: Duration, override val id: Long? = null) : TimeLimitedState(duration) {

    fun decrement() = amountOfMessagesToBeRaged--

    override fun type() = StateType.RAGE_MODE

    override fun additionalIsOverChecks(): List<() -> Boolean> {
        return listOf { amountOfMessagesToBeRaged <= 0 }
    }
}

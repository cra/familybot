package space.yaroslav.familybot.route.services.state.exhibits

import space.yaroslav.familybot.route.models.StateType
import java.time.Duration

class RageModeState(private var amountOfMessagesToBeRaged: Int, duration: Duration) : TimeLimitedState(duration) {

    fun decrement() = amountOfMessagesToBeRaged--

    override fun type() = StateType.RAGE_MODE

    override fun additionalIsOverChecks(): List<() -> Boolean> {
        return listOf { amountOfMessagesToBeRaged <= 0 }
    }
}

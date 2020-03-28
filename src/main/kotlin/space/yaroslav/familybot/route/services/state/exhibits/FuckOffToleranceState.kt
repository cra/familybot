package space.yaroslav.familybot.route.services.state.exhibits

import space.yaroslav.familybot.route.models.StateType
import java.time.Duration

class FuckOffToleranceState(duration: Duration) : TimeLimitedState(duration) {
    override fun additionalIsOverChecks(): List<() -> Boolean> {
        return emptyList()
    }

    override fun type() = StateType.FUCK_OFF_TOLERANCE
}

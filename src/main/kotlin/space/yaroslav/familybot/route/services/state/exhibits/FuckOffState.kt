package space.yaroslav.familybot.route.services.state.exhibits

import space.yaroslav.familybot.route.models.FunctionId
import space.yaroslav.familybot.route.models.StateType
import java.time.Duration

class FuckOffState(duration: Duration, override val id: Long? = null) : TimeLimitedState(duration),
    FunctionalToleranceState {

    override fun type() = StateType.FUCK_OFF

    override fun additionalIsOverChecks(): List<() -> Boolean> {
        return emptyList()
    }

    override fun disabledFunctionIds() = setOf(FunctionId.CHATTING, FunctionId.HUIFICATE)
}

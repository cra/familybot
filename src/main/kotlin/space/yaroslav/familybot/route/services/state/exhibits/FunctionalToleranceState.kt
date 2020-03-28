package space.yaroslav.familybot.route.services.state.exhibits

import space.yaroslav.familybot.route.models.FunctionId

interface FunctionalToleranceState : State {

    fun disabledFunctionIds(): Set<FunctionId>
}

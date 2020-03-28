package space.yaroslav.familybot.route.services.state.exhibits.readerwriter

import org.springframework.stereotype.Component
import space.yaroslav.familybot.route.services.state.exhibits.FuckOffToleranceState
import java.time.Duration

@Component
class FuckOffToleranceStateReaderWriter : StateReaderWriter<FuckOffToleranceState>() {
    override fun getRequiredClass() = FuckOffToleranceState::class

    override fun readInternal(data: Map<String, String>, duration: Duration): FuckOffToleranceState {
        return FuckOffToleranceState(duration)
    }

    override fun writeInternal(state: FuckOffToleranceState): Map<String, String> {
        return emptyMap()
    }
}

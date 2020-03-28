package space.yaroslav.familybot.route.services.state.exhibits.readerwriter

import org.springframework.stereotype.Component
import space.yaroslav.familybot.route.services.state.exhibits.FuckOffState
import java.time.Duration

@Component
class FuckOffStateReaderWriter : StateReaderWriter<FuckOffState>() {

    override fun getRequiredClass() = FuckOffState::class

    override fun readInternal(data: Map<String, String>, duration: Duration): FuckOffState {
        return FuckOffState(duration)
    }

    override fun writeInternal(state: FuckOffState): Map<String, String> {
        return emptyMap()
    }
}

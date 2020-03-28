package space.yaroslav.familybot.route.services.state.exhibits.readerwriter

import org.springframework.stereotype.Component
import space.yaroslav.familybot.route.services.state.exhibits.RageModeState
import java.time.Duration

@Component
class RageModeStateReaderWriter : StateReaderWriter<RageModeState>() {
    override fun getRequiredClass() = RageModeState::class

    override fun readInternal(data: Map<String, String>, duration: Duration): RageModeState {
        val messagesToRage = data[MESSAGES_TO_RAGE]?.toIntOrNull() ?: throw IllegalArgumentException()
        return RageModeState(messagesToRage, duration)
    }

    override fun writeInternal(state: RageModeState): Map<String, String> {
        return mapOf("messagesToRage" to state.amountOfMessagesToBeRaged.toString())
    }

    companion object {
        const val MESSAGES_TO_RAGE = "messagesToRage"
    }
}

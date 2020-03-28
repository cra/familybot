package space.yaroslav.familybot.route.services.state.exhibits.readerwriter

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import space.yaroslav.familybot.route.services.state.exhibits.State
import java.time.Duration
import java.time.Instant
import kotlin.reflect.KClass

abstract class StateReaderWriter<T : State> {
    private val objectMapper = jacksonObjectMapper()

    abstract fun getRequiredClass(): KClass<T>

    fun read(rawData: String, endDate: Instant): T {
        val data = parseMap(rawData)
        val duration = getDurationToEndDate(endDate)
        return readInternal(data, duration)
    }

    fun write(state: T): String {
        val data = writeInternal(state)
        return writeMap(data)
    }

    abstract fun readInternal(data: Map<String, String>, duration: Duration): T
    abstract fun writeInternal(state: T): Map<String, String>

    private fun getDurationToEndDate(endDate: Instant): Duration {
        return Duration.between(Instant.now(), endDate)
    }

    private fun parseMap(rawData: String): Map<String, String> {
        return objectMapper.readValue(rawData)
    }

    private fun writeMap(rawMap: Map<String, String>): String {
        return objectMapper.writeValueAsString(rawMap)
    }
}

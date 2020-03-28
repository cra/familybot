package space.yaroslav.familybot.repos

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import space.yaroslav.familybot.repos.ifaces.StateRepository
import space.yaroslav.familybot.route.models.StateType
import space.yaroslav.familybot.route.services.state.StateKey
import space.yaroslav.familybot.route.services.state.exhibits.State
import space.yaroslav.familybot.route.services.state.exhibits.TimeLimitedState
import java.time.Duration
import java.time.Instant

@Component
class PostgresStateRepository(
    private val template: JdbcTemplate,
) : StateRepository {

    private val objectMapper = ObjectMapper()
    private val defaultTillDate = Instant.now().plusSeconds(Duration.ofDays(1000).toSeconds())

    @Transactional
    override fun addState(stateKey: StateKey, state: State) {
        val id = template.queryForObject(
            "INSERT INTO entity (entity_user_value, entity_chat_value) " +
                "VALUES (?,?) returning entity_id",
            Int::class.java, stateKey.userId, stateKey.userId
        )
        val tillDate = getTillDate(state)
        val stateData = getJsonValue(state)

        template.update(
            "INSERT INTO state(state_till_date, state_entity_id, state_type_id, state_data)" +
                " values (?,?,?,?,?)", tillDate, id, state.type().id, stateData
        )
    }

    override fun getState(stateKey: StateKey): List<State> {
        return template.query("""
            select * from state s 
            inner join entity e on s.state_entity_id = e.entity_id
            where e.entity_user_value = ?
            and e.entity_chat_value = ?
            and s.state_start_date >= current_date
            and s.state_till_date <= current_date
        """.trimIndent(), RowMapper { rs, _ ->
            val id = rs.getInt("state_type_id")
            val stateType = StateType.findById(id)
                ?: throw IllegalArgumentException("Can't find state type with id=$id")
            return@RowMapper getStateFromJsonValue(rs.getString("state_data"), stateType)
        })
    }

    private fun getJsonValue(state: State): String {
        return objectMapper.writeValueAsString(state)
    }

    private fun getStateFromJsonValue(json: String, stateType: StateType): State {
        val clazz = stateType.clazz
        return objectMapper.readValue(json, clazz.java)
    }

    private fun getTillDate(state: State): Instant {
        return if (state is TimeLimitedState) {
            state.endTime
        } else {
            defaultTillDate
        }
    }
}
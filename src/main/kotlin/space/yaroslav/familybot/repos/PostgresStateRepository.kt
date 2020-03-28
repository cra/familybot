package space.yaroslav.familybot.repos

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import space.yaroslav.familybot.repos.ifaces.StateRepository
import space.yaroslav.familybot.route.models.StateType
import space.yaroslav.familybot.route.services.state.StateKey
import space.yaroslav.familybot.route.services.state.exhibits.State
import space.yaroslav.familybot.route.services.state.exhibits.TimeLimitedState
import space.yaroslav.familybot.route.services.state.exhibits.readerwriter.StateReaderWriter
import java.time.Duration
import java.time.Instant

@Component
class PostgresStateRepository(
    private val template: JdbcTemplate,
    private val readerWriters: List<StateReaderWriter<*>>
) : StateRepository {

    private val defaultTillDate = Instant.now().plusMillis(Duration.ofDays(1000).toMillis())

    @Transactional
    override fun addState(stateKey: StateKey, state: State) {
        val stateData = write(state)
        val id = template.queryForObject(
            """INSERT into entity (entity_user_value, entity_chat_value) 
                VALUES (?,?) on conflict (entity_chat_value, entity_user_value) 
                do update set entity_chat_value = entity_chat_value, entity_user_value = entity_user_value
                returning entity_id""",
            Int::class.java, stateKey.userId, stateKey.userId
        )
        val tillDate = getTillDate(state)

        template.update(
            """INSERT INTO 
                state(state_till_date, state_entity_id, state_type_id, state_data)
                 values (?,?,?,?)""", tillDate, id, state.type().id, stateData
        )
    }

    override fun getState(stateKey: StateKey): List<State> {
        return template.query("""
            select * from state s 
            inner join entity e on s.state_entity_id = e.entity_id
            where e.entity_user_value = ?
            and e.entity_chat_value = ?
            and s.state_from_date >= current_date
            and s.state_till_date <= current_date
        """, RowMapper { rs, _ ->
            val id = rs.getInt("state_type_id")
            val stateType = StateType.findById(id)
                ?: throw IllegalArgumentException("Can't find state type with id=$id")
            return@RowMapper read(
                rs.getString("state_data"),
                stateType,
                rs.getTimestamp("state_till_date").toInstant()
            )
        })
    }

    private fun write(state: State): String {
        val writer = readerWriters
            .find { it.getRequiredClass() == state::class } as? StateReaderWriter<in State>
            ?: throw IllegalArgumentException()

        return writer.write(state)
    }

    private fun read(rawData: String, stateType: StateType, endDate: Instant): State {
        val reader = readerWriters
            .find { it.getRequiredClass() == stateType.clazz }
            as? StateReaderWriter<out State> ?: throw IllegalArgumentException()
        return reader.read(rawData, endDate)
    }

    private fun getTillDate(state: State): Instant {
        return if (state is TimeLimitedState) {
            state.endTime
        } else {
            defaultTillDate
        }
    }
}

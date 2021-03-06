package space.yaroslav.familybot.executors.command

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import kotlinx.coroutines.delay
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ForceReplyKeyboard
import org.telegram.telegrambots.meta.bots.AbsSender
import space.yaroslav.familybot.common.utils.toUser
import space.yaroslav.familybot.executors.Configurable
import space.yaroslav.familybot.models.Command
import space.yaroslav.familybot.models.FunctionId
import space.yaroslav.familybot.models.Phrase
import space.yaroslav.familybot.repos.ifaces.CommandHistoryRepository
import space.yaroslav.familybot.services.dictionary.Dictionary
import space.yaroslav.familybot.telegram.BotConfig

const val ROULETTE_MESSAGE = "Выбери число от 1 до 6"

@Component
@Deprecated(message = "Replaced with BetExecutor")
class RouletteExecutor(
    private val commandHistoryRepository: CommandHistoryRepository,
    private val dictionary: Dictionary,
    config: BotConfig
) : CommandExecutor(config), Configurable {

    override fun getFunctionId(): FunctionId {
        return FunctionId.PIDOR
    }

    override fun command(): Command {
        return Command.ROULETTE
    }

    override fun execute(update: Update): suspend (AbsSender) -> Unit {
        val now = LocalDate.now()
        val commands = commandHistoryRepository.get(
            update.toUser(), LocalDateTime.of(LocalDate.of(now.year, now.month, 1), LocalTime.MIDNIGHT)
                .toInstant(ZoneOffset.UTC)
        )
        if (commands.filter { it.command == command() }.size > 1) {
            return {
                it.execute(SendMessage(update.message.chatId, dictionary.get(Phrase.ROULETTE_ALREADY_WAS)))
                delay(2000)
                it.execute(SendMessage(update.message.chatId, dictionary.get(Phrase.PIDOR)))
            }
        }
        return {
            it.execute(
                SendMessage(update.message.chatId, dictionary.get(Phrase.ROULETTE_MESSAGE))
                    .setReplyMarkup(ForceReplyKeyboard().setSelective(true))
                    .setReplyToMessageId(update.message.messageId)
            )
        }
    }

    override fun isLoggable(): Boolean {
        return false
    }
}

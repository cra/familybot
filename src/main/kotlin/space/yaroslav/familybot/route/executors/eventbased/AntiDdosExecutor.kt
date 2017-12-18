package space.yaroslav.familybot.route.executors.eventbased

import org.springframework.stereotype.Component
import org.telegram.telegrambots.api.methods.send.SendMessage
import org.telegram.telegrambots.api.objects.Message
import org.telegram.telegrambots.api.objects.Update
import org.telegram.telegrambots.bots.AbsSender
import space.yaroslav.familybot.common.toUser
import space.yaroslav.familybot.repos.ifaces.HistoryRepository
import space.yaroslav.familybot.route.executors.Executor
import space.yaroslav.familybot.route.models.Command
import space.yaroslav.familybot.route.models.Priority

@Component
class AntiDdosExecutor(val repository: HistoryRepository) : Executor {
    override fun execute(update: Update): (AbsSender) -> Unit {
        return { it.execute(SendMessage(update.message.chatId, "Сука, еще раз нажмешь и я те всеку")) }
    }

    override fun canExecute(message: Message): Boolean {
        val commandsByUser = repository
                .get(message.from.toUser(telegramChat = message.chat))
                .groupBy { it.command }
        return commandsByUser.filterValues { it.size >= 5 }.keys
                .contains(Command
                        .values()
                        .find { message.text.contains(it.command, true) })

    }

    override fun priority(): Priority {
        return Priority.HIGH
    }
}
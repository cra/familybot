package space.yaroslav.familybot.route.executors.command

import org.springframework.stereotype.Component
import org.telegram.telegrambots.api.methods.send.SendMessage
import org.telegram.telegrambots.api.objects.Update
import org.telegram.telegrambots.bots.AbsSender
import space.yaroslav.familybot.common.utils.random
import space.yaroslav.familybot.common.utils.toChat
import space.yaroslav.familybot.repos.ifaces.ChatLogRepository
import space.yaroslav.familybot.route.models.Command

@Component
class TopHistoryExecutor(private val chatLogRepository: ChatLogRepository) : CommandExecutor {
    override fun command(): Command {
        return Command.TOP_HISTORY
    }

    override fun execute(update: Update): (AbsSender) -> Unit {
        val randomStory =
            chatLogRepository.getAll()
                .subList(0, 700)
                .filterNot { it.contains("http", ignoreCase = true) }
                .random()
        return { it.execute(SendMessage(update.toChat().id, randomStory)) }
    }
}

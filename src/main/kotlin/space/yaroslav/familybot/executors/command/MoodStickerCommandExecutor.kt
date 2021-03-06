package space.yaroslav.familybot.executors.command

import java.time.LocalDate
import java.time.ZoneOffset
import kotlinx.coroutines.delay
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender
import space.yaroslav.familybot.common.CommandByUser
import space.yaroslav.familybot.common.User
import space.yaroslav.familybot.common.utils.send
import space.yaroslav.familybot.common.utils.sendRandomSticker
import space.yaroslav.familybot.common.utils.toUser
import space.yaroslav.familybot.models.Command
import space.yaroslav.familybot.models.stickers.StickerPack
import space.yaroslav.familybot.repos.ifaces.CommandHistoryRepository
import space.yaroslav.familybot.telegram.BotConfig

@Component
class MoodStickerCommandExecutor(
    private val historyRepository: CommandHistoryRepository,
    config: BotConfig
) : CommandExecutor(config) {
    override fun command() = Command.WHATS_MOOD_TODAY

    override fun execute(update: Update): suspend (AbsSender) -> Unit {

        if (isInvokedToday(update.toUser())) {
            return {}
        }

        return {
            it.send(update, "Какой ты сегодня?")
            delay(1000)
            it.sendRandomSticker(update, StickerPack.YOU_ARE_TODAY)
        }
    }

    private fun isInvokedToday(user: User): Boolean {
        val commandsFromUserToday = historyRepository.get(
            user,
            from = LocalDate.now().atTime(0, 0).toInstant(ZoneOffset.UTC)
        ).map(CommandByUser::command)
        return commandsFromUserToday.any { it == command() }
    }
}

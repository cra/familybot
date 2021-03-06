package space.yaroslav.familybot.executors

import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.springframework.beans.factory.annotation.Autowired
import space.yaroslav.familybot.executors.command.CommandExecutor
import space.yaroslav.familybot.executors.command.MeCommandExecutor
import space.yaroslav.familybot.infrastructure.UpdateBuilder
import space.yaroslav.familybot.suits.CommandExecutorTest

class MeExecutorTest : CommandExecutorTest() {

    @Autowired
    lateinit var meCommandExecutor: MeCommandExecutor

    override fun getCommandExecutor(): CommandExecutor = meCommandExecutor

    override fun executeTest() {
        val update = UpdateBuilder().simpleCommandFromUser(meCommandExecutor.command())
        runBlocking { meCommandExecutor.execute(update).invoke(testSender) }
        val actions = testSender.actions
        Assert.assertTrue("Should be exactly one action with stats", actions.size == 1)
    }
}

package space.yaroslav.familybot.repos.ifaces

import space.yaroslav.familybot.common.Chat
import space.yaroslav.familybot.models.Phrase
import space.yaroslav.familybot.models.PhraseTheme
import space.yaroslav.familybot.repos.PhraseThemeSetting
import space.yaroslav.familybot.repos.RawPhrase

interface PhraseDictionaryRepository {

    fun getPhraseTheme(chat: Chat): PhraseTheme

    fun getDefaultPhraseTheme(): PhraseTheme

    fun getPhraseSettings(): List<PhraseThemeSetting>

    fun getPhrases(phrase: Phrase, phraseTheme: PhraseTheme): List<String>

    fun getAllPhrases(phrase: Phrase): List<String>

    fun getAll(): List<RawPhrase>

    fun addPhrase(phrase: Phrase, phraseTheme: PhraseTheme, value: String)
}

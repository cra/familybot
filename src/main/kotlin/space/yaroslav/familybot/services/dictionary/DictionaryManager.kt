package space.yaroslav.familybot.services.dictionary

import space.yaroslav.familybot.models.Phrase
import space.yaroslav.familybot.models.PhraseTheme

interface DictionaryManager {

    fun getAll(): Map<PhraseTheme, Map<Phrase, List<PhraseValueWithId>>>

    fun addPhraseValue(phraseTheme: PhraseTheme, phrase: Phrase, value: String)
}

data class PhraseValueWithId(
    val id: Int,
    val value: String
)

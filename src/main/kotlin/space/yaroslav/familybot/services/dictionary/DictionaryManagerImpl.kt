package space.yaroslav.familybot.services.dictionary

import org.springframework.stereotype.Component
import space.yaroslav.familybot.models.Phrase
import space.yaroslav.familybot.models.PhraseTheme
import space.yaroslav.familybot.repos.RawPhrase
import space.yaroslav.familybot.repos.ifaces.PhraseDictionaryRepository

@Component
class DictionaryManagerImpl(
    private val dictionaryRepository: PhraseDictionaryRepository
) : DictionaryManager {

    override fun getAll(): Map<PhraseTheme, Map<Phrase, List<PhraseValueWithId>>> {
        return dictionaryRepository.getAll()
            .groupBy(RawPhrase::theme)
            .mapValues { (_, value) ->
                value.groupBy(
                    keySelector = RawPhrase::type,
                    valueTransform = { PhraseValueWithId(it.valueId, it.value) })
            }
    }

    override fun addPhraseValue(phraseTheme: PhraseTheme, phrase: Phrase, value: String) {
        dictionaryRepository.addPhrase(phrase, phraseTheme, value)
    }
}

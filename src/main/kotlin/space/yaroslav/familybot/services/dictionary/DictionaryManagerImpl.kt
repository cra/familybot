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
            .fold(mutableMapOf()) { accumulator: MutableMap<PhraseTheme, MutableMap<Phrase, List<PhraseValueWithId>>>, rawPhrase: RawPhrase ->
                accumulator.apply {
                    compute(rawPhrase.theme) { _, phrasesToValues ->
                        phrasesToValues?.apply {
                            compute(rawPhrase.type) { _, values ->
                                values?.plus(PhraseValueWithId(rawPhrase.valueId, rawPhrase.value)) ?: listOf(
                                    PhraseValueWithId(rawPhrase.valueId, rawPhrase.value)
                                )
                            }
                        } ?: mutableMapOf(
                            rawPhrase.type to listOf(
                                PhraseValueWithId(
                                    rawPhrase.valueId,
                                    rawPhrase.value
                                )
                            )
                        )
                    }
                }
            }
    }

    override fun addPhraseValue(phraseTheme: PhraseTheme, phrase: Phrase, value: String) {
        dictionaryRepository.addPhrase(phrase, phraseTheme, value)
    }
}

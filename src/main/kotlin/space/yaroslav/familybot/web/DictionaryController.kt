package space.yaroslav.familybot.web

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import space.yaroslav.familybot.models.Phrase
import space.yaroslav.familybot.models.PhraseTheme
import space.yaroslav.familybot.services.dictionary.DictionaryManager
import space.yaroslav.familybot.services.dictionary.PhraseValueWithId

@RestController
@RequestMapping("v1/dictionary")
class DictionaryController(
    private val dictionaryManager: DictionaryManager
) {

    @GetMapping
    fun getAll(): Map<PhraseTheme, Map<Phrase, List<PhraseValueWithId>>> {
        return dictionaryManager.getAll()
    }

    @GetMapping("/{phraseTheme}")
    fun getAllByTheme(
        @PathVariable phraseTheme: String
    ): Map<Phrase, List<PhraseValueWithId>> {
        return dictionaryManager.getAll()[PhraseTheme.lookup(phraseTheme)] ?: emptyMap()
    }

    @GetMapping("/{phraseTheme}/{phraseType}")
    fun getAllByThemeAndPhraseType(
        @PathVariable phraseTheme: String,
        @PathVariable phraseType: String
    ): List<PhraseValueWithId> {
        return dictionaryManager
            .getAll()[PhraseTheme.lookup(phraseTheme)]
            ?.get(Phrase.lookup(phraseType))
            ?: emptyList()
    }

    @PutMapping("/{phraseTheme}/{phraseType}")
    fun addPhrase(
        @RequestBody phraseValue: PhraseValue,
        @PathVariable phraseTheme: String,
        @PathVariable phraseType: String
    ) {
        dictionaryManager.addPhraseValue(PhraseTheme.lookup(phraseTheme), Phrase.lookup(phraseType), phraseValue.value)
    }

    @PatchMapping("/{phraseTheme}/{phraseType}/{phraseId}")
    fun updatePhrase(
        @RequestBody phraseValue: PhraseValue,
        @PathVariable phraseTheme: String,
        @PathVariable phraseType: String,
        @PathVariable phraseId: Int
    ) {
    }

    @DeleteMapping("/{phraseTheme}/{phraseType}/{phraseId}")
    fun deletePhrase(
        @PathVariable phraseTheme: String,
        @PathVariable phraseType: String,
        @PathVariable phraseId: Int
    ) {
    }
}

class PhraseValue(
    val value: String
)

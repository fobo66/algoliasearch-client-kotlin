package model.synonym

import com.algolia.search.exception.EmptyListException
import com.algolia.search.exception.EmptyStringException
import com.algolia.search.model.synonym.Synonym
import com.algolia.search.model.synonym.SynonymType
import objectIDA
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import shouldEqual
import shouldFailWith


@RunWith(JUnit4::class)
internal class TestSynonym {


    @Test
    fun tokenShouldNotBeEmpty() {
        EmptyStringException::class shouldFailWith { Synonym.Placeholder.Token("") }
    }

    @Test
    fun tokenFormat() {
        Synonym.Placeholder.Token("token").raw shouldEqual "<token>"
    }

    @Test
    fun oneWayInputShouldNotBeEmpty() {
        EmptyStringException::class shouldFailWith { Synonym.OneWay(objectIDA, "", listOf()) }
    }

    @Test
    fun oneWaySynonymsShouldNotBeEmpty() {
        EmptyListException::class shouldFailWith { Synonym.OneWay(objectIDA, "input", listOf()) }
    }

    @Test
    fun multiWaySynonymsShouldNotBeEmpty() {
        EmptyListException::class shouldFailWith { Synonym.MultiWay(objectIDA, listOf()) }
    }

    @Test
    fun alternativeWordShouldNotBeEmpty() {
        EmptyStringException::class shouldFailWith {
            Synonym.AlternativeCorrections(objectIDA, "", listOf(), SynonymType.Typo.One)
        }
    }

    @Test
    fun alternativeCorrectionsShouldNotBeEmpty() {
        EmptyListException::class shouldFailWith {
            Synonym.AlternativeCorrections(objectIDA, "word", listOf(), SynonymType.Typo.One)
        }
    }

    @Test
    fun placeholderReplacementsShouldNotBeEmpty() {
        val token = Synonym.Placeholder.Token("token")

        EmptyListException::class shouldFailWith {
            Synonym.Placeholder(objectIDA, token, listOf())
        }
    }
}
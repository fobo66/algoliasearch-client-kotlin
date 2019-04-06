package documentation.guides.results

import com.algolia.search.dsl.settings
import com.algolia.search.model.search.TypoTolerance
import documentation.index
import runBlocking
import kotlin.test.Ignore
import kotlin.test.Test


@Ignore
internal class GuideForwardReplicas {


    @Test
    fun snippet() {
        runBlocking {
            val settings = settings {
                typoTolerance = TypoTolerance.Strict
            }

            index.setSettings(settings, forwardToReplicas = true)
        }
    }
}
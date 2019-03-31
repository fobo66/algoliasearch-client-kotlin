package documentation.parameters.geosearch

import com.algolia.search.dsl.query
import com.algolia.search.model.search.AroundRadius
import documentation.index
import runBlocking
import kotlin.test.Test


internal class DocAroundRadius {

//    aroundRadius: AroundRadius = [AroundRadius.InMeters](#parameter-option-radius-in-meters)
//    | [AroundRadius.All](#parameter-option-all)

    @Test
    fun queryInMeters() {
        runBlocking {
            val query = query("query") {
                aroundRadius = AroundRadius.InMeters(1000)
            }

            index.search(query)
        }
    }

    @Test
    fun queryAll() {
        runBlocking {
            val query = query("query") {
                aroundRadius = AroundRadius.All
            }

            index.search(query)
        }
    }
}
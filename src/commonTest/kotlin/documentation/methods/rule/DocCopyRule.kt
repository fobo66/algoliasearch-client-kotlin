package documentation.methods.rule

import documentation.index
import indexName
import runBlocking
import kotlin.test.Ignore
import kotlin.test.Test


@Ignore
internal class DocCopyRule {

//    suspend fun [Index](#method-param-indexnamesrc).copyRules(
//        [destination](#method-param-indexnamedest): __IndexName__,
//        requestOptions: __RequestOptions?__ = null
//    ): RevisionIndex

    @Test
    fun copyRule() {
        runBlocking {
            index.copyRules(indexName)
        }
    }
}
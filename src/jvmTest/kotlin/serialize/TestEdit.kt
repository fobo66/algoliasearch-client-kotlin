package serialize

import com.algolia.search.model.queryRule.Edit
import com.algolia.search.serialize.*
import kotlinx.serialization.json.json
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import unknown


@RunWith(JUnit4::class)
internal class TestEdit : TestSerializer<Edit>(Edit) {
    override val items = listOf(
        Edit(unknown) to json {
            KeyType to KeyRemove.toLowerCase()
            KeyDelete to unknown
        },
        Edit(unknown, unknown) to json {
            KeyType to KeyReplace
            KeyDelete to unknown
            KeyInsert to unknown
        }
    )
}
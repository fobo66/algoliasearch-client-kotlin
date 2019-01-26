package serialize

import attributeA
import com.algolia.search.model.Attribute
import kotlinx.serialization.json.JsonLiteral
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
internal class TestAttribute : TestSerializer<Attribute>(Attribute) {

    override val items = listOf(
        attributeA to JsonLiteral(attributeA.raw)
    )
}
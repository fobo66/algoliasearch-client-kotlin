package serialize.settings

import attributeA
import attributeB
import com.algolia.search.model.settings.SearchableAttribute
import com.algolia.search.model.settings.SearchableAttribute.*
import com.algolia.search.serialize.KeyOrdered
import com.algolia.search.serialize.KeyUnordered
import kotlinx.serialization.json.JsonLiteral
import serialize.TestSerializer


internal class TestSearchableAttribute : TestSerializer<SearchableAttribute>(SearchableAttribute) {

    override val items = listOf(
        Ordered(attributeA) to JsonLiteral("$KeyOrdered($attributeA)"),
        Unordered(attributeA) to JsonLiteral("$KeyUnordered($attributeA)"),
        Default(attributeA) to JsonLiteral(attributeA.raw),
        Default(attributeA, attributeB) to JsonLiteral("$attributeA, $attributeB")
    )
}
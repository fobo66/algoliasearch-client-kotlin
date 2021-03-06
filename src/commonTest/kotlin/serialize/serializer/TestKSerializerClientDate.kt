package serialize.serializer

import com.algolia.search.helper.DateISO8601
import com.algolia.search.model.ClientDate
import com.algolia.search.model.Time
import com.algolia.search.serialize.KSerializerClientDate
import kotlinx.serialization.json.JsonLiteral
import serialize.TestSerializer

internal class TestKSerializerClientDate : TestSerializer<ClientDate>(KSerializerClientDate) {

    private val date = Time.getCurrentTimeMillis()
    private val format = DateISO8601.format(date)
    private val formatMillis = DateISO8601.format(date, true)

    override val items = listOf(
        ClientDate(format) to JsonLiteral(format),
        ClientDate(formatMillis) to JsonLiteral(formatMillis)
    )
}

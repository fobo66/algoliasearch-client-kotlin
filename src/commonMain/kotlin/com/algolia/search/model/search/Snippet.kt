package com.algolia.search.model.search

import com.algolia.search.helper.toAttribute
import com.algolia.search.model.Attribute
import com.algolia.search.model.Raw
import com.algolia.search.serialize.regexSnippet
import kotlinx.serialization.Decoder
import kotlinx.serialization.Encoder
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer

@Serializable(Snippet.Companion::class)
public data class Snippet(
    /**
     * Attribute to snippet.
     * Use "*" to snippet all attributes.
     */
    val attribute: Attribute,
    /**
     * Optional word count.
     * Engine default: 10
     */
    val count: Int? = null
) : Raw<String> {

    override val raw = attribute.raw + if (count != null) ":$count" else ""

    override fun toString(): String {
        return raw
    }

    companion object : KSerializer<Snippet> {

        private val serializer = String.serializer()

        override val descriptor = serializer.descriptor

        override fun serialize(encoder: Encoder, value: Snippet) {
            serializer.serialize(encoder, value.raw)
        }

        override fun deserialize(decoder: Decoder): Snippet {
            val string = serializer.deserialize(decoder)

            val findSnippet = regexSnippet.find(string)

            return when {
                findSnippet != null -> Snippet(
                    findSnippet.groupValues[1].toAttribute(),
                    findSnippet.groupValues[2].toInt()
                )
                else -> Snippet(string.toAttribute())
            }
        }
    }
}

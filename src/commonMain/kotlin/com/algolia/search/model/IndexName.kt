package com.algolia.search.model

import com.algolia.search.exception.EmptyStringException
import com.algolia.search.helper.StringUTF8
import com.algolia.search.helper.toIndexName
import com.algolia.search.serialize.RouteIndexesV1
import kotlinx.serialization.Decoder
import kotlinx.serialization.Encoder
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.internal.StringSerializer


@Serializable(IndexName.Companion::class)
data class IndexName(
    override val raw: String
) : Raw<String> {

    init {
        if (raw.isEmpty()) throw EmptyStringException("IndexName")
    }

    private fun encode(): StringUTF8 {
        return StringUTF8.encode(raw)
    }

    internal fun toPath(suffix: String? = null): String {
        return "$RouteIndexesV1/${encode().string}" + (suffix ?: "")
    }

    override fun toString(): String {
        return raw
    }

    companion object : KSerializer<IndexName> {

        private val serializer = StringSerializer

        override val descriptor = serializer.descriptor

        override fun serialize(encoder: Encoder, obj: IndexName) {
            serializer.serialize(encoder, obj.raw)
        }

        override fun deserialize(decoder: Decoder): IndexName {
            return serializer.deserialize(decoder).toIndexName()
        }
    }
}
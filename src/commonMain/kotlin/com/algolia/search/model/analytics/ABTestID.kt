package com.algolia.search.model.analytics

import com.algolia.search.helper.toABTestID
import com.algolia.search.model.Raw
import kotlinx.serialization.Decoder
import kotlinx.serialization.Encoder
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer

/**
 * ID of an [ABTest].
 */
@Serializable(ABTestID.Companion::class)
public data class ABTestID(override val raw: Long) : Raw<Long> {

    override fun toString(): String {
        return raw.toString()
    }

    companion object : KSerializer<ABTestID> {

        private val serializer = Long.serializer()

        override val descriptor = serializer.descriptor

        override fun serialize(encoder: Encoder, value: ABTestID) {
            serializer.serialize(encoder, value.raw)
        }

        override fun deserialize(decoder: Decoder): ABTestID {
            val long = serializer.deserialize(decoder)

            return long.toABTestID()
        }
    }
}

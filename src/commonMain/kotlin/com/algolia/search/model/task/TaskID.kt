package com.algolia.search.model.task

import com.algolia.search.model.Raw
import com.algolia.search.toTaskID
import kotlinx.serialization.Decoder
import kotlinx.serialization.Encoder
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.internal.LongSerializer


@Serializable(TaskID.Companion::class)
data class TaskID(override val raw: Long) : Raw<Long> {

    override fun toString(): String {
        return raw.toString()
    }

    companion object : KSerializer<TaskID> {

        private val serializer = LongSerializer

        override val descriptor = serializer.descriptor

        override fun serialize(encoder: Encoder, obj: TaskID) {
            serializer.serialize(encoder, obj.raw)
        }

        override fun deserialize(decoder: Decoder): TaskID {
            val long = serializer.deserialize(decoder)

            return long.toTaskID()
        }
    }
}
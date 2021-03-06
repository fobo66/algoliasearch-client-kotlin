package com.algolia.search.model.search

import com.algolia.search.model.settings.Settings
import com.algolia.search.serialize.JsonNonStrict
import com.algolia.search.serialize.asJsonInput
import kotlinx.serialization.Decoder
import kotlinx.serialization.Encoder
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.builtins.list
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonLiteral

@Serializable(IgnorePlurals.Companion::class)
public sealed class IgnorePlurals {

    /**
     * Enables the ignore plurals functionality, where singulars and plurals are considered equivalent (foot = feet).
     * The languages supported here are either every language or those set by [Settings.queryLanguages]
     */
    public object True : IgnorePlurals()

    /**
     * Which disables ignore plurals, where singulars and plurals are not considered the same for matching purposes
     * (foot will not find feet).
     */
    public object False : IgnorePlurals()

    /**
     * A list of [Language] for which ignoring plurals should be enabled.
     * This list of [queryLanguages] will override any values that you may have set in [Settings.queryLanguages].
     */
    public data class QueryLanguages(val queryLanguages: List<Language>) : IgnorePlurals() {

        public constructor(vararg queryLanguage: Language) : this(queryLanguage.toList())
    }

    @Serializer(IgnorePlurals::class)
    companion object : KSerializer<IgnorePlurals> {

        override fun serialize(encoder: Encoder, value: IgnorePlurals) {
            when (value) {
                is True -> Boolean.serializer().serialize(encoder, true)
                is False -> Boolean.serializer().serialize(encoder, false)
                is QueryLanguages -> Language.list.serialize(encoder, value.queryLanguages)
            }
        }

        override fun deserialize(decoder: Decoder): IgnorePlurals {
            return when (val element = decoder.asJsonInput()) {
                is JsonArray -> QueryLanguages(element.map {
                    JsonNonStrict.fromJson(Language, it)
                })
                is JsonLiteral -> if (element.boolean) True else False
                else -> throw Exception()
            }
        }
    }
}

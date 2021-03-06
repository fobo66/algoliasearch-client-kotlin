package com.algolia.search.endpoint

import com.algolia.search.configuration.CallType
import com.algolia.search.dsl.requestOptionsBuilder
import com.algolia.search.model.IndexName
import com.algolia.search.model.response.revision.RevisionIndex
import com.algolia.search.model.settings.NumericAttributeFilter
import com.algolia.search.model.settings.SearchableAttribute
import com.algolia.search.model.settings.Settings
import com.algolia.search.model.settings.SettingsKey
import com.algolia.search.serialize.Json
import com.algolia.search.serialize.JsonNonStrict
import com.algolia.search.serialize.KeyAttributesToIndex
import com.algolia.search.serialize.KeyForwardToReplicas
import com.algolia.search.serialize.KeyNumericAttributesToIndex
import com.algolia.search.serialize.KeySlaves
import com.algolia.search.serialize.RouteSettings
import com.algolia.search.serialize.merge
import com.algolia.search.serialize.toJsonNoDefaults
import com.algolia.search.transport.RequestOptions
import com.algolia.search.transport.Transport
import io.ktor.http.HttpMethod
import kotlinx.serialization.builtins.list
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.json

internal class EndpointSettingsImpl(
    private val transport: Transport,
    override val indexName: IndexName
) : EndpointSettings {

    override suspend fun getSettings(requestOptions: RequestOptions?): Settings {
        val path = indexName.toPath("/$RouteSettings")
        val json = transport.request<JsonObject>(HttpMethod.Get, CallType.Read, path, requestOptions)
        // The following lines handle the old names of attributes, thus providing backward compatibility.
        val settings = JsonNonStrict.fromJson(Settings.serializer(), json)
        val attributesToIndex = json.getArrayOrNull(KeyAttributesToIndex)?.let {
            Json.fromJson(SearchableAttribute.list, it)
        }
        val numericAttributesToIndex = json.getArrayOrNull(KeyNumericAttributesToIndex)?.let {
            Json.fromJson(NumericAttributeFilter.list, it)
        }
        val slaves = json.getArrayOrNull(KeySlaves)?.let {
            Json.fromJson(IndexName.list, it)
        }

        return settings.copy(
            searchableAttributes = settings.searchableAttributes ?: attributesToIndex,
            numericAttributesForFiltering = settings.numericAttributesForFiltering ?: numericAttributesToIndex,
            replicas = settings.replicas ?: slaves
        )
    }

    private suspend fun setSettings(
        settings: Settings,
        resetToDefault: List<SettingsKey>,
        forwardToReplicas: Boolean?,
        requestOptions: RequestOptions?,
        indexName: IndexName
    ): RevisionIndex {
        val resets = json { resetToDefault.forEach { it.raw to JsonNull } }
        val body = settings.toJsonNoDefaults().merge(resets).toString()
        val options = requestOptionsBuilder(requestOptions) {
            parameter(KeyForwardToReplicas, forwardToReplicas)
        }

        return transport.request(HttpMethod.Put, CallType.Write, indexName.toPath("/$RouteSettings"), options, body)
    }

    override suspend fun setSettings(
        settings: Settings,
        resetToDefault: List<SettingsKey>,
        forwardToReplicas: Boolean?,
        requestOptions: RequestOptions?
    ): RevisionIndex {
        return setSettings(settings, resetToDefault, forwardToReplicas, requestOptions, indexName)
    }
}

package com.algolia.search.client

import com.algolia.search.model.*
import com.algolia.search.model.APIKey
import com.algolia.search.model.multiple_index.IndexQuery
import com.algolia.search.model.multiple_index.MultipleQueriesStrategy
import com.algolia.search.model.search.Query
import com.algolia.search.serialize.*
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import kotlinx.serialization.json.json
import kotlinx.serialization.json.jsonArray


internal fun HttpRequestBuilder.setApplicationId(applicationID: ApplicationID) {
    header("X-Algolia-Application-Id", applicationID.raw)
}

internal fun HttpRequestBuilder.setApiKey(apiKey: APIKey) {
    header("X-Algolia-API-Key", apiKey.raw)
}

internal fun HttpRequestBuilder.setRequestOptions(requestOptions: RequestOptions?) {
    requestOptions?.headers?.forEach { header(it.key, it.value) }
    requestOptions?.urlParameters?.forEach { parameter(it.key, it.value) }
}

internal fun HttpRequestBuilder.setForwardToReplicas(forwardToReplicas: Boolean?) {
    forwardToReplicas?.let { parameter(KeyForwardToReplicas, it) }
}

internal fun HttpRequestBuilder.setQueries(queries: Collection<IndexQuery>, strategy: MultipleQueriesStrategy) {
    body = json {
        KeyRequests to jsonArray {
            queries.forEach {
                +json {
                    KeyIndexName to it.indexName.raw
                    KeyParams to it.query.encodeNoNulls().urlEncode()
                }
            }
        }
        KeyStrategy to strategy.raw
    }.toString()
}

internal fun HttpRequestBuilder.setBody(query: Query?) {
    body = query?.encodeNoNulls()?.toString() ?: "{}"
}
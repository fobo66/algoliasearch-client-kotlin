package com.algolia.search.configuration

import com.algolia.search.model.APIKey
import com.algolia.search.model.ApplicationID
import com.algolia.search.transport.analyticsHost
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.features.logging.LogLevel


data class ConfigurationAnalytics(
    override val applicationID: ApplicationID,
    override val apiKey: APIKey,
    override val writeTimeout: Long = defaultWriteTimeout,
    override val readTimeout: Long = defaultReadTimeout,
    override val logLevel: LogLevel = defaultLogLevel,
    override val hosts: List<RetryableHost> = listOf(analyticsHost),
    override val engine: HttpClientEngine? = null,
    override val httpClient: HttpClient = engine.httpClient(logLevel)
) : Configuration
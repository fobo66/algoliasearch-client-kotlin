package com.algolia.search.request

import com.algolia.search.model.ClusterName
import com.algolia.search.serialize.KeyCluster
import com.algolia.search.serialize.KeyHitsPerPage
import com.algolia.search.serialize.KeyParams
import com.algolia.search.serialize.KeyQuery
import kotlinx.serialization.Optional
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class RequestSearchUserID(
    @Optional @SerialName(KeyQuery) val query: String? = null,
    @Optional @SerialName(KeyCluster) val clusterName: ClusterName? = null,
    @Optional @SerialName(KeyParams) val page: Int? = null,
    @Optional @SerialName(KeyHitsPerPage) val hitsPerPage: Int? = null
)
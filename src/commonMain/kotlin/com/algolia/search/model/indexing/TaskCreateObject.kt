package com.algolia.search.model.indexing

import com.algolia.search.model.ObjectID
import com.algolia.search.model.common.Task
import com.algolia.search.model.common.TaskID
import kotlinx.serialization.Serializable


@Serializable
data class TaskCreateObject(
    val createdAt: String,
    val objectID: ObjectID,
    override val taskID: TaskID
) : Task
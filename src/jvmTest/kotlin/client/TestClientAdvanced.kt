package client

import com.algolia.search.saas.data.Settings
import com.algolia.search.saas.data.TaskStatus
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import shouldEqual


@RunWith(JUnit4::class)
class TestClientAdvanced {

    @Test
    fun getTask() {
        runBlocking {
            val task = index.setSettings(Settings())

            println(index.getTask(task))
        }
    }

    @Test
    fun waits() {
        runBlocking {
            index.apply {
                setSettings(Settings()).wait().status shouldEqual TaskStatus.Published
            }
        }
    }
}
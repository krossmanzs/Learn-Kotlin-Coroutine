package krossmanzs.coroutine

import kotlinx.coroutines.*
import org.junit.jupiter.api.Test
import java.util.concurrent.Executors

/**
 * Memberi Nama Coroutine
 *
 * Selain dispatchere, salah satu coroutine context yang
 * lain adalah CoroutineName
 *
 * CoroutineName bisa kita gunakan untuk mengubah nama coroutine
 * sesuai dengan yang kita mau, hal ini sangat bermanfaat ketika
 * sedang melakukan proses debugging
 */

class CoroutineNameTest {
    @Test
    fun testName() {
        val dispatcher = Executors.newFixedThreadPool(10).asCoroutineDispatcher()
        val scope = CoroutineScope(dispatcher)
        val job = scope.launch(CoroutineName("Parent")) {
            println("Run in thread ${Thread.currentThread().name}")
            withContext(CoroutineName("Child")) {
                println("Run in thread ${Thread.currentThread().name}")
            }
        }

        runBlocking {
            job.join()
        }
    }
}
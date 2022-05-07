package krossmanzs.coroutine

import kotlinx.coroutines.*
import org.junit.jupiter.api.Test
import java.util.concurrent.Executors

/**
 * Menggabungkan Context Element
 *
 * CoroutineContext adalah kumpulan dari element-element,
 * contoh turunannya adalah Job, CoroutineDispatcher dan yang
 * terakhir dipelajari adalah CoroutineName
 *
 * CoroutineContext memiliki method plus, sehingga sebenarnya kita
 * bisa menggabungkan beberapa context element secara sekaligus,
 * misal Dispatcher dan CoroutineName misalnya
 */

class CombineContextElementTest {
    @Test
    fun testCombineContextElement() {
        val dispatcher = Executors.newSingleThreadExecutor().asCoroutineDispatcher()
        val scope = CoroutineScope(dispatcher.plus(CoroutineName("Parent")))
        val job = scope.launch {
            println("Run in thread ${Thread.currentThread().name}")
            withContext(CoroutineName("Child") + Dispatchers.IO) {
                println("Run in thread ${Thread.currentThread().name}")
            }
        }

        runBlocking {
            job.join()
        }
    }
}
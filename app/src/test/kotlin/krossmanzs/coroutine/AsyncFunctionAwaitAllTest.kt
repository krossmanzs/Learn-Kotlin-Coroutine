package krossmanzs.coroutine

import kotlinx.coroutines.*
import org.junit.jupiter.api.Test
import kotlin.system.measureTimeMillis

/*
    awaitAll Function pada Async Function
    - Pada job, tersedia joinAll untuk menunggu semua Launch
        coroutine selesai
    - Pada Async, hal itu bisa dilakukan dengan awaitAll untuk
        menunggu semua Deferred selesai mengembalikan value
    - awaitAll merupakan generic function, dan mengembalikan List<T>
        data hasil dari semua Deferrednya
 */

class AsyncFunctionAwaitAllTest {
    @Test
    fun testSuspendAsyncAwaitAll() {
        runBlocking {
            val totalTime = measureTimeMillis {
                val foo: Deferred<Int> = GlobalScope.async { getFoo() }
                val bar: Deferred<Int> = GlobalScope.async { getBar() }
                val total: Int = awaitAll(foo, bar).sum()
                println("Total: $total")
            }

            println("Total time: $totalTime")
        }
    }

    suspend fun getFoo() : Int {
        delay(1_000)
        return 10
    }

    suspend fun getBar() : Int {
        delay(1_000)
        return 2
    }
}
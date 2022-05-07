package krossmanzs.coroutine

import kotlinx.coroutines.*
import org.junit.jupiter.api.Test

/**
 * CoroutineScope Function
 *
 * Pada kasus sederhana, kita bisa menggunakan
 * CoroutineScope function untuk menggabungkan
 * beberapa suspend function
 *
 * Saat ada error di coroutine yang terdapat di dalam
 * coroutine scope function tersebut, maka semua coroutine
 * pun akan dibatalkan
 */

class CoroutineScopeFunctionTest {
    suspend fun getFoo() : Int {
        delay(1_000)
        return 10
    }

    suspend fun getBar() : Int {
        delay(1_000)
        return 10
    }

    suspend fun getSum() : Int = coroutineScope{
        val foo = async { getFoo() }
        val bar = async { getBar() }
        foo.await() + bar.await();
    }

    @Test
    fun testCoroutineScopeFunction() {
        val scope = CoroutineScope(Dispatchers.IO)
        val job = scope.launch {
            val result = getSum()
            println("Result: $result")
        }

        runBlocking {
            job.join()
        }
    }
}
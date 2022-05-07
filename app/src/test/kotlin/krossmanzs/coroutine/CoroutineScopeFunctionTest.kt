package krossmanzs.coroutine

import kotlinx.coroutines.*
import org.junit.jupiter.api.Test
import java.util.concurrent.Executors

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

    /**
     * Coroutine Scope Parent & Child
     *
     * Saat membuat coroutine scope, sebenarnya kita telah
     * membuat child scope dari parent scopenya
     *
     * Saat kita membuat child scope, secara otomatis child scope
     * akan menggunakan dispatcher milik parent dan saat kita
     * membatalkan parent scope, maka semua child scopenya pun akan
     * dibatalkan
     */

    @Test
    fun testChildDispatcher() {
        val parentDispatcher = Executors.newFixedThreadPool(10).asCoroutineDispatcher()
        val parentScope = CoroutineScope(parentDispatcher)

        val job = parentScope.launch {
            println("Parent Scope run in ${Thread.currentThread().name}")
            coroutineScope {
                launch {
                    delay(2_000)
                    println("Child Scope run in ${Thread.currentThread().name}")
                }
            }
        }

        runBlocking {
            job.cancelAndJoin()
        }
    }
}
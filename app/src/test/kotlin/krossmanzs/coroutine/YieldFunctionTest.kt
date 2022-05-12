package krossmanzs.coroutine

import kotlinx.coroutines.*
import org.junit.jupiter.api.Test
import java.util.concurrent.Executors

/**
 * Yield Function
 * Suspend function berjalan secara sequential, artinya
 * jika ada sebuah suspend function yang panjang dan lama,
 * ada baiknya kita beri kesempatan ke suspend function lainnya
 * untuk dijalankan
 *
 * Coroutine berjalan secara concurrent, artinya 1 dispatcher
 * bisa digunakan untuk mengeksekusi beberapa coroutine secara
 * bergantian. Saat coroutine kita berjalan, dan jika kita ingin
 * memberi kesempatan ke coroutine yang lain untuk berjalan, kita
 * kita bisa menggunakan yield function
 *
 * Yield function bisa di cancel, artinya jika sebuah coroutine telah
 * dibatalkan, maka secara otomatis yield function akan throw
 * CancellationException
 */

class YieldFunctionTest {
    suspend fun runJob(number: Int) {
        println("Start job $number in thread ${Thread.currentThread().name}")
        yield()
        println("End job $number in thread ${Thread.currentThread().name}")
    }

    @Test
    fun testYieldFunction() {
        val dispatcher = Executors.newFixedThreadPool(10).asCoroutineDispatcher()
        val scope = CoroutineScope(dispatcher)

        runBlocking {
            scope.launch { runJob(1) }
            scope.launch { runJob(2) }

            delay(2_000)
        }
    }

}
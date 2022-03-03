package krossmanzs.coroutine

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import java.util.Date
import kotlin.concurrent.thread

class CoroutineTest {

    /**
     * MEMBUAT COROUTINE
     *
     * Coroutine tidak dapat berjalan sendiri, dia harus berjalan
     * di ddalam sebuah scope
     *
     * Untuk membuatnya, kita bisa menggunakan method launch()
     *
     * Kita bisa memanggil suspend function di dalam coroutine
     */

    suspend fun hello() {
        delay(1_000)
        println("Hello World")
    }

    @Test
    fun testCoroutine() {
        // running coroutine secara async
        GlobalScope.launch {
            hello()
        }
        println("MENUNGGU")
        runBlocking {
            delay(2_000)
        }
        println("SELESAI")
    }

    @Test
    fun testThread() {
        repeat(100_000) {
            thread {
                Thread.sleep(1_000)
                println("Done $it : ${Date()}")
            }
        }

        println("Waiting")
        Thread.sleep(10_000)
        println("Finish")
    }

    @Test
    fun testCoroutineMany() {
        repeat(100_000) {
            GlobalScope.launch {
                delay(1_000)
                println("Done $it : ${Date()} : ${Thread.currentThread().name}")
            }
        }

        println("Waiting")
        runBlocking {
            delay(10_000)
        }
        println("Finish")
    }

}
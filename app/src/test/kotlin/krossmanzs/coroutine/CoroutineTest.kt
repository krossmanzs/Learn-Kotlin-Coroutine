package krossmanzs.coroutine

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

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

}
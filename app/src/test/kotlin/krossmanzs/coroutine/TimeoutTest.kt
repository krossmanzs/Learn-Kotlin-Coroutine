package krossmanzs.coroutine

import kotlinx.coroutines.*
import org.junit.jupiter.api.Test
import java.util.Date

class TimeoutTest {

    /**
     * withTimeout Function
     *
     * Melakukan timeout sebanyak n milidetik setelah itu
     * menghentikan coroutine karena throw timeoutException
     */

    @Test
    fun testWithTimeout() {
        runBlocking {
            val job = GlobalScope.launch {
                println("Start coroutine")
                withTimeout(5_000) {
                    repeat(100) {
                        delay(1_000)
                        println("$it ${Date()}")
                    }
                }
                println("Finish coroutine")
            }

            job.join()
        }
    }

    /**
     * withTimeoutOrNull Function
     *
     * Melakukan timeout sebanyak n milidetik tetapi tidak
     * menghentikan coroutine, justru mengembalikan nilai null
     */

    @Test
    fun testTimeoutOrNull() {
        runBlocking {
            val job = GlobalScope.launch {
                println("Start coroutine")
                withTimeoutOrNull(5_000) {
                    repeat(100) {
                        delay(1_000)
                        println("$it ${Date()}")
                    }
                }
                println("Finish coroutine")
            }

            job.join()
        }
    }

}
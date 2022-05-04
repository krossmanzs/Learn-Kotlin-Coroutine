package krossmanzs.coroutine

import kotlinx.coroutines.*
import org.junit.jupiter.api.Test

class NonCancellableContextTest {
    @Test
    fun testCancelFinally() {
        runBlocking {
            val job = GlobalScope.launch {
                try {
                    println("Start Job")
                    delay(1_000)
                    println("Job end")
                } finally {
                    println(isActive)
                    // program dibawah tidak dijalankan
                    // karena delay job nya sudah di cancel
                    delay(1_000)
                    println("Finally")
                }
            }
            job.cancelAndJoin()
        }
    }

    @Test
    fun testNonCancellableFinally() {
        runBlocking {
            val job = GlobalScope.launch {
                try {
                    println("Start Job")
                    delay(1_000)
                    println("Job end")
                } finally {
                    withContext(NonCancellable) {
                        println(isActive)
                        delay(1_000)
                        println("Finally")
                    }
                }
            }
            job.cancelAndJoin()
        }
    }
}
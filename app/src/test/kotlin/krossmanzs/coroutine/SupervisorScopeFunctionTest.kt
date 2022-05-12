package krossmanzs.coroutine

import kotlinx.coroutines.*
import org.junit.jupiter.api.Test
import java.util.concurrent.Executors

/**
 * SupervisorScope Function.
 * Kadang ada kondisi dimana kita tidak memiliki akses
 * untuk mengubah sebuah coroutine scope.
 *
 * Karena secara default sifatnya adalah Job, maka kita bisa
 * menggunakan supervisorScope function untuk membuat coroutine
 * yang sifatnya SupervisorJob
 */

class SupervisorScopeFunctionTest {
    @Test
    fun testSupervisorScopeFunction() {
        val dispatcher = Executors.newFixedThreadPool(10).asCoroutineDispatcher()
        val scope = CoroutineScope(dispatcher + Job())

        runBlocking {
            scope.launch {
                supervisorScope { // coroutine yang ini akan berjalan secara independen
                    launch {
                        delay(2_000)
                        println("Child 1 finish")
                    }
                    launch {
                        delay(1_000)
                        throw IllegalArgumentException("Child 2 failed")
                    }
                    launch {
                        delay(3_000)
                        println("Child 3 finish")
                    }
                }
            }

            delay(3_000)
        }
    }
}
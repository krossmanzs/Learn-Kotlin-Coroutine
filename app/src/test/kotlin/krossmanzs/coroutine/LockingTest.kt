package krossmanzs.coroutine

import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.junit.jupiter.api.Test
import java.util.concurrent.Executors

/**
 * Mutex (Mutual Exclusion)
 * adalah salah satu fitur di Kotlin Coroutine untuk melakukan proses
 * locking.
 *
 * Dengan menggunakan mutex, kita bisa pastikan bahwa hanya ada 1 coroutine
 * yang bisa mengakses kode tersebut, code coroutine yang lain akan menunggu
 * sampai coroutine pertama selesai.
 *
 * Seperti estafet, terdapat satu kunci dan yang memegang kunci tersebut bisa
 * berjalan, sebaliknya dia harus menunggu sampai mendapat kuncinya.
 */
class LockingTest {
    @Test
    fun testRaceCondition() {
        var counter : Int = 0
        val dispatcher = Executors.newFixedThreadPool(10).asCoroutineDispatcher()
        val scope = CoroutineScope(dispatcher)

        repeat(100) {
            scope.launch {
                repeat(1000) {
                    counter++
                }
            }
        }

        runBlocking {
            delay(5_000)
            println("Total counter : $counter")
        }
    }

    @Test
    fun testMutex() {
        val mutex = Mutex()
        var counter = 0;
        val dispatcher = Executors.newFixedThreadPool(10).asCoroutineDispatcher()
        val scope = CoroutineScope(dispatcher)

        repeat(100) {
            scope.launch {
                repeat(1000) {
                    mutex.withLock {
                        counter++
                    }
                }
            }
        }

        runBlocking {
            delay(5_000)
            println("Total counter : $counter")
        }
    }
}
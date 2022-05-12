package krossmanzs.coroutine

import kotlinx.coroutines.*
import org.junit.jupiter.api.Test

/**
 * awaitCancellation Function
 * Secara default, sebuah coroutine akan berhenti ketika
 * seluruh code selesai dijalankan.
 *
 * Jika ada kebutuhan kita tidak mau coroutine berhenti sampai
 * di Job nya di cancel, maka kita bisa menggunakan function
 * awaitCancellation.
 *
 * Fungsi ini akan throw CancellationException jika Job di cancel
 * dan tidak akan menghentikan coroutine jika belum di cancel.
 */

class AwaitCancellationFunctionTest {
    @Test
    fun testAwaitCancellation() {
        runBlocking {
            // langsung menggunakan launch karena
            // runBlocking sudah menggunakan GlobalScope
            val job = launch {
                try {
                    println("Do something")
                    awaitCancellation()
                } finally {
                    println("Coroutine Cancelled")
                }
            }

            delay(5_000)
            job.cancelAndJoin()
        }
    }
}
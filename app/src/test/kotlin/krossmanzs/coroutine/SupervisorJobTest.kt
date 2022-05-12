package krossmanzs.coroutine

import kotlinx.coroutines.*
import org.junit.jupiter.api.Test
import java.util.concurrent.Executors

/**
 * Job
 * Secara default ketika kita membuat coroutine scope atau
 * menjalankan coroutine, tipe coroutine tersebut adalah Job
 *
 * Dalam Job, saat terjadi error di salah satu coroutine, maka
 * error tersebut akan di propagate ke parentnya dan secara
 * otomatis parent akan membatalkan semua coroutine
 *
 * Misal ada 3 child dalam 1 parent, child pertamanya gagal maka
 * dia akan escalate ke atas(parent) dan meng-cancel chil yang lainnya.
 * Lalu memberi tahu atas parentnya bahwa coroutine yang dia handle
 * itu error/dibatalkan
 */

class SupervisorJobTest {
    @Test
    fun testJob() {
        val dispatcher = Executors.newFixedThreadPool(10).asCoroutineDispatcher()
        val scope = CoroutineScope(Job() + dispatcher) // Job() sebenarnya sudah ada dan tidak wajib di tulis
        val job1 = scope.launch {
            delay(2_000)
            println("Job 1 complete")
        }

        val job2 = scope.launch {
            delay(1_000)
            throw IllegalArgumentException("Job 2 failed")
        }

        runBlocking {
            joinAll(job1,job2)
        }
    }

    /**
     * Supervisor Job
     * adalah tipe Job lainnya, bisa menjadikan setiap coroutine memiliki
     * kemampuan untuk error secara mandiri.
     *
     * Hal ini berakibat jika ada coroutine error, parent tidak akan
     * membatalkan seluruh coroutine yang lain.
     */
    @Test
    fun testSupervisorJob() {
        val dispatcher = Executors.newFixedThreadPool(10).asCoroutineDispatcher()
        val scope = CoroutineScope(SupervisorJob() + dispatcher)
        val job1 = scope.launch {
            delay(2_000)
            println("Job1 Finish")
        }

        val job2 = scope.launch {
            delay(1_000)
            throw IllegalArgumentException("Job2 failed")
        }

        runBlocking {
            joinAll(job1, job2)
        }
    }
}
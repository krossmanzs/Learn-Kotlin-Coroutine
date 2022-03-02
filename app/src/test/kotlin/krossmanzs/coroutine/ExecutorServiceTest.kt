package krossmanzs.coroutine

import org.junit.jupiter.api.Test
import java.util.*
import java.util.concurrent.Executors

class ExecutorServiceTest {

    // Membuat ExecutorService dengan 1 Thread
    @Test
    fun testSingleThreadPool() {
        val executorService = Executors.newSingleThreadExecutor()
        repeat(10) {
            val runnable = Runnable {
                Thread.sleep(1_000)
                println("Done $it ${Thread.currentThread().name} ${Date()}")
            }
            executorService.execute(runnable) // memasukkan kedalam antrian
            println("Selesai memasukkan runnable $it")
        }

        println("MENUNGGU")
        Thread.sleep(11_000)
        println("SELESAI")
    }

    // Membuat ExecutorService dengan n thread
    @Test
    fun testFixThreadPool() {
        val executorService = Executors.newFixedThreadPool(3)
        repeat(10) {
            val runnable = Runnable {
                Thread.sleep(1_000)
                println("Done $it ${Thread.currentThread().name} ${Date()}")
            }
            executorService.execute(runnable) // memasukkan kedalam antrian
            println("Selesai memasukkan runnable $it")
        }

        println("MENUNGGU")
        Thread.sleep(11_000)
        println("SELESAI")
    }

    // Membuat ExecutorService dengan thread akan meningkat sesuai kebutuhan
    @Test
    fun testCacheThreadPool() {
        val executorService = Executors.newCachedThreadPool()
        repeat(100) {
            val runnable = Runnable {
                Thread.sleep(1_000)
                println("Done $it ${Thread.currentThread().name} ${Date()}")
            }
            executorService.execute(runnable) // memasukkan kedalam antrian
            println("Selesai memasukkan runnable $it")
        }

        println("MENUNGGU")
        Thread.sleep(11_000)
        println("SELESAI")
    }

}
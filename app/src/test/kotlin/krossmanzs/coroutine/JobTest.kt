package krossmanzs.coroutine

import kotlinx.coroutines.*
import org.junit.jupiter.api.Test
import java.util.Date

class JobTest {

    @Test
    fun testJob() {
        runBlocking {
            GlobalScope.launch {
                delay(2_000)
                println("Coroutine Done ${Thread.currentThread().name}")
            }
        }
    }

    @Test
    fun testJobLazy() {
        runBlocking {
            val job: Job = GlobalScope.launch(start = CoroutineStart.LAZY) {
                delay(2000)
                println("Coroutine Done ${Thread.currentThread().name}")
            }

            job.start()

            delay(3_000)
        }
    }

    @Test
    fun testJobJoin() {
        runBlocking {
            val job: Job = GlobalScope.launch(start = CoroutineStart.LAZY) {
                delay(2000)
                println("Coroutine Done ${Thread.currentThread().name}")
            }

            job.join()
        }
    }

    @Test
    fun testJobCancel() {
        runBlocking {
            val job: Job = GlobalScope.launch {
                delay(2000)
                println("Coroutine Done ${Thread.currentThread().name}")
            }

            job.cancel()

            delay(3_000)
        }
    }

    /**
     * joinAll Function
     *
     * joinAll Function digunakan untuk membuat coroutine lebih dari
     * satu sekaligus.
     */

    @Test
    fun testJoinAll() {
        runBlocking {
            println("START: ${Date()}")

            val job1: Job = GlobalScope.launch(start = CoroutineStart.LAZY) {
                delay(2_000)
                println("Coroutine Done ${Thread.currentThread().name} : ${Date()}")
            }

            val job2: Job = GlobalScope.launch(start = CoroutineStart.LAZY) {
                delay(3_000)
                println("Coroutine Done ${Thread.currentThread().name} : ${Date()}")
            }

            joinAll(job2, job1)
        }
    }
}
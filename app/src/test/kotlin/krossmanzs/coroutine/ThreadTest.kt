package krossmanzs.coroutine

import org.junit.jupiter.api.Test
import java.util.*
import kotlin.concurrent.thread

class ThreadTest {
    @Test
    internal fun testThreadName() {
        val threadName = Thread.currentThread().name
        println("Running in thread $threadName")
    }

    @Test
    fun testNewThread() {
        // BAWAAN JAVA
//        val runnable = Runnable {
//            println(Date())
//            Thread.sleep(2_000)
//            println("Finish : ${Date()}")
//        }
//
//        val thread = Thread(runnable)
//        thread.start()

        // KOTLIN VERSION :) lebih simple
        thread(start = true) {
            println(Date())
            Thread.sleep(2_000)
            println("Finish : ${Date()}")
        }

        println("WAIT FOR thread TO FINISH")
        Thread.sleep(3_000)
        println("END FUNCTION")
    }

    @Test
    fun testMultipleThread() {
        val thread1 = thread(start = false) {
            println(Date())
            Thread.sleep(2_000)
            println("Finish Thread 1 : ${Thread.currentThread().name} : ${Date()}")
        }

        val thread2 = thread(start = false) {
            println(Date())
            Thread.sleep(2_000)
            println("Finish Thread 2 : ${Thread.currentThread().name} : ${Date()}")
        }

        // keren aja gitu pake also :V
        thread1.start().also { thread2.start() }

        println("WAIT FOR thread TO FINISH")
        Thread.sleep(3_000)
        println("END FUNCTION")
    }
}
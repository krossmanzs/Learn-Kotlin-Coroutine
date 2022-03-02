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
}
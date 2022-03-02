package krossmanzs.coroutine

import org.junit.jupiter.api.Test

class ThreadTest {
    @Test
    internal fun testThreadName() {
        val threadName = Thread.currentThread().name
        println("Running in thread $threadName")
    }
}
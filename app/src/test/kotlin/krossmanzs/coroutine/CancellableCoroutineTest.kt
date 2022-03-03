package krossmanzs.coroutine

import kotlinx.coroutines.*
import org.junit.jupiter.api.Test
import java.util.Date

class CancellableCoroutineTest {

    @Test
    fun testCanNotCancel() {
        runBlocking {

            val job = GlobalScope.launch {
                println("Start coroutine ${Date()}")
                Thread.sleep(2_000)
                println("End coroutine ${Date()}")
            }

            job.cancel()
            delay(3_000)
        }
    }

    @Test
    fun testCancellable() {
        runBlocking {

            val job = GlobalScope.launch {
                if (!isActive) throw CancellationException()
                println("Start coroutine ${Date()}")

                ensureActive()
                Thread.sleep(2_000)

                ensureActive()
                println("End coroutine ${Date()}")
            }

            job.cancel()
            delay(3_000)
        }
    }

}
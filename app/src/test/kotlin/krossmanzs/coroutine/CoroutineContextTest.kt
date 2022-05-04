package krossmanzs.coroutine

import kotlinx.coroutines.*
import org.junit.jupiter.api.Test
import kotlin.coroutines.CoroutineContext

/*
    Coroutine Context
    adalah sebuah kumpulan data CoroutineContext.Element,
    yang paling utama contohnya adalah Job (turunan dari
    CoroutineContext.Element) dan CoroutineDispatcher

    Mirip kaya list, kumpulan data dari CoroutineContext.Element
 */

class CoroutineContextTest {
    @OptIn(ExperimentalStdlibApi::class)
    @Test
    fun testCoroutineContext() {
        runBlocking {
            val job = GlobalScope.launch {
                val context: CoroutineContext = coroutineContext
                println(context)
                println(context[Job])
                println(context[CoroutineDispatcher])

                val job: Job? = context[Job]
                job?.cancel()
            }
            job.join()
        }
    }
}
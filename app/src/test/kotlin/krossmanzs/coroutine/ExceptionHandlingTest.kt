package krossmanzs.coroutine

import kotlinx.coroutines.*
import org.junit.jupiter.api.Test

/**
 * [Exception Propaganda]
 *
 * Secara garis besar, exception di coroutine itu ada yang
 * di ekspose ke yang memanggil coroutine ada yang tidak.
 *
 * Pada launch, exception tidak akan di ekspose ketika memanggil
 * function join, namun pada async, exception akan di expose ketika
 * memanggil function await.
 */

class ExceptionHandlingTest {
    @Test
    fun testExceptionLaunch() {
        runBlocking {
            val job = GlobalScope.launch {
                println("Start job")
                throw IllegalArgumentException()
            }

            job.join()
            println("Finish")
        }
    }

    @Test
    fun testExceptionAsync() {
        runBlocking {
            val deferred = GlobalScope.async {
                println("Start coroutine")
                throw IllegalArgumentException()
            }

            try {
                deferred.await()
            } catch (error: IllegalArgumentException) {
                println("Error")
            } finally {
                println("Finish")
            }
        }
    }

    /**
     * Coroutine Exception Handler
     * Jika kita ingin mengatur cara penangkapan exception di
     * coroutine, hal ini bisa dilakukan dengan menggunakan
     * interface CoroutineExceptionHandler.
     *
     * CoroutineExceptionHandler adalah turunan dari CoroutineContext.Element,
     * sehingga kita bisa menambahkannya kedalam coroutine context.
     *
     * Jenis CancellationException (dan turunannya) tidak akan diteruskan
     * ke exception handler.
     *
     * Coroutine exception handler hanya jalan di launch, tidak jalan di async,
     * untuk async, kita harus menangkap exception nya secara manual.
     */
    @Test
    fun testCoroutineExceptionHandler() {
        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            println("Ups, error ${throwable.message} on ${coroutineContext[CoroutineName.Key]}")
        }
        
        runBlocking {
            val job = GlobalScope.launch(exceptionHandler + CoroutineName("Test_Exception_Handler")) {
                println("Start coroutine")
                throw IllegalArgumentException("Illegal argument bang")
            }

            job.join()
            println("Finish")
        }
    }
}
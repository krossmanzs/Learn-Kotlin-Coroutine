package krossmanzs.coroutine

import kotlinx.coroutines.*
import org.junit.jupiter.api.Test
import java.util.concurrent.Executors

/*
    withContext Function
    kita tahu bahwa saat melakukan delay(), suspend function
    tersebut akan di trigger di thread yang berbeda

    Kita bisa menggunakan withContext() untuk menjalankan code
    program kita dalam coroutine di thread yang berbeda dengan
    thread coroutine awalnya

    Function withContext() aslinya bisa kita gunakan untuk mengganti
    CoroutineContext, namun karena CoroutineDispatcher adalah turunan
    CoroutineContext, jadi kita bisa otomatis mengganti thread yang
    akan digunakan di coroutine menggunakan function withContext()
 */

class WithContextFunctionTest {
    @Test
    fun testWithContext() {
        val dispatcherClient = Executors.newFixedThreadPool(10).asCoroutineDispatcher()
        runBlocking {
            val job = GlobalScope.launch(Dispatchers.IO) {
                println("1. This code run in thread ${Thread.currentThread().name}")
                withContext(dispatcherClient) {
                    println("2. This code run in thread ${Thread.currentThread().name}")
                }
                println("3. This code run in thread ${Thread.currentThread().name}")
                withContext(dispatcherClient) {
                    println("4. This code run in thread ${Thread.currentThread().name}")
                }
            }

            job.join();
        }
    }
}
package krossmanzs.coroutine

import kotlinx.coroutines.*
import org.junit.jupiter.api.Test
import java.util.concurrent.Executors

/*
    Membuat Coroutine Dispatcher
    kadang saat membuat aplikasi kita ingin fleksibel untuk
    menentukan thread mana yg akan kita gunakan untuk
    menjalankan coroutine

    Contoh, kita ingin membedakan thread untuk layer web, layer
    http client, dll. Hal tersebut bisa kita atasi dengan membuat
    Coroutine Dispatcher sendiri dan hal ini sangat di rekomendasikan

    Caranya untuk membuat Coroutine Dispatcher secara manual, kita bisa
    melakukannya dengan cara menggunakan ExecutorService
 */

class CreateCoroutineDispatcherTest {
    @Test
    fun testMembuatCoroutineDispatcher() {
        // cara penggunaanya dengan mengconvert
        // executorService menjadi coroutineDispatcher
        val dispatcherService: ExecutorCoroutineDispatcher = Executors.newFixedThreadPool(10).asCoroutineDispatcher()
        val dispatcherWeb = Executors.newSingleThreadExecutor().asCoroutineDispatcher()

        runBlocking {
            println("runBlocking run in thread ${Thread.currentThread().name}")
            val jobService = GlobalScope.launch(dispatcherService) {
                println("jobService run in thread ${Thread.currentThread().name}")
            }
            val jobWeb = GlobalScope.launch(dispatcherWeb) {
                println("jobWeb run in thread ${Thread.currentThread().name}")
            }
            joinAll(jobService, jobWeb)
        }
    }
}
package krossmanzs.coroutine

import kotlinx.coroutines.*
import org.junit.jupiter.api.Test

/*
    Coroutine Dispatcher
    Selain object Job pada CoroutineContext, terdapat
    juga CoroutineDispatcher

    Ini digunakan untuk menentukan thread mana yang
    bertanggung jawab untuk mengeksekusi coroutine

    Secara default sudah diset, tetapi kita bisa mengubahnya

    Coroutine tidak bisas dibandingkan dengan thread,
    karena coroutine dijalankan di dalam Thread.
    Bedanya thread itu bisa gonta ganti coroutine

    Mirip threadpool pada materi Executor Service

    Dispatchers
    - Dispatchers.Default
      ini adalah default dispatcher, isinya minimal 2 thread atau
      sebanyak cpu(mana yg lebih banyak). Ini cocok untuk proses
      coroutine yang cpu-bound

    - Dispatchers.IO
      dispatcher berisikan thread sesuai dengan kebutuhan, ketika
      membutuhkan dibuat, sebaliknya akan dihapus, ini mirip dengan
      thread pool di executor service. Dispatcher ini akan sharing
      dengan Default Dispatcher

    - Dispatchers.Main
      ini adalah dispatchers yang berisikan main thread UI,
      dispatcher ini cocok untuk running di thread main seperti
      di Java Swing, JavaFX atau Android. Untuk menggunakannya, kita
      harus meambahkan library UI sesuai dengan framework UI nya
 */

/*
    Unconfined vs Confined
    - Dispatchers.Unconfined
      ini adalah dispatcher yang tidak menunjuk thread apapun, biasanya
      akan melanjutkan thread di coroutine sebelumnya

    - Dispatchers.Confined (tanpa parameter)
      ini adalah dispatcher yang akan melanjutkan thread dari coroutine
      sebelumnya

      Perbedaan
      pada Unconfined, thread bisa berubah di tengah jalan jika memang
      terdapat code yang melakukan perubahan thread (fleksibel).
      Misal dia pakai A, tiba tiba di tengah jalan pake B maka threadnya berubah
      menjadi B
 */

class CoroutineDispatcherTest {
    @Test
    fun testDispatcher() {
        println("Unit test in thread ${Thread.currentThread().name}")
        runBlocking {
            println("runBlocking in thread ${Thread.currentThread().name}")
            val job1 = GlobalScope.launch(Dispatchers.Default) {
                println("Job 1 run in thread ${Thread.currentThread().name}")
            }
            val job2 = GlobalScope.launch(Dispatchers.IO) {
                println("Job 2 run in thread ${Thread.currentThread().name}")
            }
            listOf(job1, job2).forEach {
                it.join();
            }
        }
    }

    @Test
    fun testUnconfined() {
        // launch
        // lalu pada delay kita berharap threadnya di
        // lemparkan kepada thread lain sehingga threadnya
        // seharusnya berubah
        runBlocking {
            println("runBlocking ${Thread.currentThread().name}")

            GlobalScope.launch(Dispatchers.Unconfined) {
                println("Unconfined : in thread ${Thread.currentThread().name}")
                delay(1_000)
                println("Unconfined : in thread ${Thread.currentThread().name}")
            }
            GlobalScope.launch {
                println("confined : in thread ${Thread.currentThread().name}")
                delay(1_000)
                println("confined : in thread ${Thread.currentThread().name}")
            }

            delay(3_000)
        }
    }
}
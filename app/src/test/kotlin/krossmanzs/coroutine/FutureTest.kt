package krossmanzs.coroutine

import org.junit.jupiter.api.Test
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future
import kotlin.system.measureTimeMillis

class FutureTest {

    val executorService = Executors.newFixedThreadPool(10)

    fun getFoo(): Int{
        Thread.sleep(3_000)
        println("Foo selesai")
        return 10
    }

    fun getBar(): Int {
        Thread.sleep(2_000)
        println("Bar selesai")
        return 10
    }

    @Test
    fun testNonParallel() {
        val time = measureTimeMillis {
            val foo = getFoo()
            val bar = getBar()
            val result = foo + bar
            println("Total = $result")
        }

        println("Total time = $time")
    }

    /**
     *  CALLABLE
     *
     *  Jika kita ingin mengeksekusi sebuah kode yang mengembalikan data, kita bisa
     *  menggunakan interface Callable, dimana terdapat method call dan return value
     *  nya adalah generic.
     *
     *  Kita bisa menggunakan ExecutorService.submit(callable) untuk mengeksekusi,
     *  Callable, dan hasilnya adalah sebuah Future<T>
     *
     *  FUTURE
     *
     *  Dengan Future, kita bisa mengecek status apakah proses telah selesai,
     *  atau bisa mendapatkan data hasil return callable, atau bahkan
     *  membatalkan proses callable yang sedang berjalan
     */

    @Test
    fun testFuture() {
        val time = measureTimeMillis {
            val foo: Future<Int> = executorService.submit(Callable { getFoo() })
            val bar: Future<Int> = executorService.submit(Callable { getBar() })
            val result = foo.get() + bar.get()
            println("Total = $result")
        }

        println("Total time = $time")
    }
}
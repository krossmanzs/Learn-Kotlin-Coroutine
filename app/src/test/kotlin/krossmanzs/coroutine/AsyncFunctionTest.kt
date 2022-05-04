package krossmanzs.coroutine

import kotlinx.coroutines.*
import org.junit.jupiter.api.Test
import kotlin.system.measureTimeMillis

/*
    Async Function
    - Tidak hanya launch yang ada pada coroutine, terdapat
        Function Async
    - Perbedaannya launch mengembalikan Job, Async mengembalikan
        Deferred
    - Deferred adalah turunan dari Job, Deferred membawa value hasil
        dari async function
    - Deferred mirip fungsi Promise atau Future, dimana datanya bisa
        kita olah nantinya
    - Jika ingin menunggu data Deferred sampai ada, kita bisa menggunakan
        fungsi await()

    Tips:
    - Jika ingin mengambil datanya maka bisa memakai Async
    - Jika tidak ingim mengambil datanya maka bisa memakai Launch
 */

class AsyncFunctionTest {
    @Test
    fun testSuspendAsync() {
        runBlocking {
            val total = measureTimeMillis {
                val foo: Deferred<Int> = GlobalScope.async { getFoo() }
                val bar: Deferred<Int> = GlobalScope.async { getBar() }
                val total = foo.await() + bar.await()
                println("Total is: $total")
            }

            println("Time total: $total")
        }
    }

    suspend fun getFoo() : Int {
        delay(1_000)
        return 10
    }

    suspend fun getBar() : Int {
        delay(1_000)
        return 5
    }
}
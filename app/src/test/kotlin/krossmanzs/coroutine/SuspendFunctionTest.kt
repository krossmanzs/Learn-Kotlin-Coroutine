package krossmanzs.coroutine

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import java.util.*

class SuspendFunctionTest {

    /**
     * SUSPEND FUNCTION
     *
     * Suspend Computation adalah komputasi yang bisa
     * ditangguhkan (ditunda waktu eksekusinya)
     *
     * Dengan suspending function, kita bisa menangguhkan waktu
     * eksekusi sebuah function, tanpa harus mem-block thread yang
     * sedang menjalankannya sperti Thread.sleep di java
     */

    @Test
    fun testSuspendFunction() {
        println("MULAI :${Date()} : ${Thread.currentThread().name}")
        runBlocking {
            helloWorld()
        }
        println("SELESAI : ${Date()} : ${Thread.currentThread().name}")
    }

    suspend fun helloWorld() {
        println("Hello : ${Date()} : ${Thread.currentThread().name}")
        delay(2_000)
        println("World : ${Date()} : ${Thread.currentThread().name}")
    }

}
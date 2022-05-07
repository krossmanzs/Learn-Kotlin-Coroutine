package krossmanzs.coroutine

import kotlinx.coroutines.*
import org.junit.jupiter.api.Test
import java.util.concurrent.Executors

/**
 * Coroutine Parent & Child
 *
 * Coroutine bisa memiliki child coroutine
 *
 * Saat membuat coroutine child, secara otomatis kita akan
 * mewarisi coroutine context yang ada di coroutine parent
 * dan coroutine parent akan menunggu sampai eksekusi coroutine
 * child nya selesai semua
 */

class CoroutineParentChildTest {
    @Test
    fun testParentChild() {
        val parentContext = Executors.newFixedThreadPool(10).asCoroutineDispatcher()
        val parentScope = CoroutineScope(parentContext)

        runBlocking {
            val job = parentScope.launch {
                launch {
                    delay(2_000)
                    println("Child 1 finish")
                }

                launch {
                    delay(4_000)
                    println("Child 2 finish")
                }

                delay(1_000)
                println("Parent finish")
            }

            job.join()
        }
    }

    /**
     * cancelChildren Function
     *
     * Coroutine akan direpresentasikan sebagai job(Deferred
     * tuturan dari Job), dan di Job kita bisa mendapatkan semua
     * childrean nya menggunakan field children
     *
     * Selain itu ada sebuah function bernama cancelChildren, function
     * ini bisa kita gunakan untuk membatalkan semua coroutine children
     *
     * Jika kita membatalkan Job parent, kita tidak perlu membatalkan
     * childrennya secara manual, karena saat Job di batalkan, semua child
     * nya akan dibatalkan
     *
     * Note:
     * Ini gatau kenapa kok child 2 nya malah ikutan padahal sudah di cancelChildren
     */
    @Test
    fun testCancelChildren() {
        runBlocking {
            val job = GlobalScope.launch {
                launch {
                    delay(2_000)
                    println("Child 1 Finish")
                }

                launch {
                    delay(4_000)
                    println("Child 2 Finish")
                }

                delay(1_000)
                println("Parent Finish")
            }

            job.cancelChildren()
            job.join()
        }
    }
}
package krossmanzs.coroutine

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.selects.select
import org.junit.jupiter.api.Test

/**
 * Select Function
 * memungkinkan kita untuk menunggu beberapa suspending
 * function dan memilih yang pertama datanya tersedia
 *
 * Select Function bisa digunakan di Deferred dan juga
 * Channel
 *
 * Untuk Deferred, kita bisa menggunakan onAwait dan untuk
 * ReceiveChannel, kita bisa menggunakan onReceive
 */

class SelectTest {
    @Test
    fun testSelectDeferred() {
        val scope = CoroutineScope(Dispatchers.IO)
        val deferred1 = scope.async { delay(1000); 1000 }
        val deferred2 = scope.async { delay(2000); 2000 }
        runBlocking {
            val win = select<Int> {
                deferred1.onAwait { it }
                deferred2.onAwait { it }
            }
            println("Win $win")
        }
    }

    @Test
    fun testSelectChannel() {
        val scope = CoroutineScope(Dispatchers.IO)
        val channel1 = scope.produce<Int> { delay(1000); send(1000) }
        val channel2 = scope.produce<Int> { delay(2000); send(2000) }
        runBlocking {
            val win = select<Int> {
                channel1.onReceive { it }
                channel2.onReceive { it }
            }
            println("Win: $win")
        }
    }
}
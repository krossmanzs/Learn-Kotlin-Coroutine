package krossmanzs.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

/**
 * Actor
 *
 * Saat kita menggunakan produce() function, kita membuat
 * coroutine sekaligus sebagai channel sendernya.
 *
 * Untuk membuat coroutine sekaligus channel receiver, kita
 * bisa menggunakan actor() function.
 *
 * Konsepnya seperti dikenal dengan konsep Actor Model
 */

class ActorTest {
    @Test
    fun testActor() {
        val scope = CoroutineScope(Dispatchers.IO)
        runBlocking {
            val sendChannel: SendChannel<Int> = scope.actor(capacity = 10) {
                repeat(10) { println("Receive ${receive()}") }
            }

            val job = scope.launch {
                repeat(10) {
                    println("Send $it")
                    sendChannel.send(it)
                }
            }

            job.join()
            sendChannel.close()
        }
    }
}
package krossmanzs.coroutine

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.broadcast
import org.junit.jupiter.api.Test

/**
 * Broadcast Channel
 *
 * Secara default channel hanya boleh memiliki
 * 1 receiver
 *
 * Namun Kotlin Coroutine mendukung Broadcast Channel,
 * ini adalah channel khusus yang receiver nya bisa
 * lebih dari satu
 *
 * Setiap kita mengirim data ke channel ini, secara otomatis
 * semua receiver bisa mendapatkan data tersebut.
 *
 * BroadcastChannel memiliki function openSubscription() untuk
 * membuat ReceiveChannel baru.
 *
 * Broadcast channel tidak mendukung kapasitas buffer 0 dan
 * UNLIMITED
 */

class BroadcastChannelTest {
    @Test
    fun testBroadcastChannel() {
        val broadcastChannel = BroadcastChannel<Int>(capacity = 10)
        val receiver1 = broadcastChannel.openSubscription()
        val receiver2 = broadcastChannel.openSubscription()
        val scope = CoroutineScope(Dispatchers.IO)
        runBlocking {
            scope.launch {
                repeat(10) { broadcastChannel.send(it) }
            }.join()
            scope.launch {
                repeat(10) { println("Receiver 1: ${receiver1.receive()}") }
            }.join()
            scope.launch {
                println()
                repeat(10) { println("Receiver 2: ${receiver2.receive()}") }
            }.join()
        }
    }

    /**
     * Broadcast Function
     *
     * Sama seperti produce function, untuk membuat broadcast
     * channel secara langsung dengan coroutinenya, kita bisa
     * menggunakan function broadcast di coroutine scope
     *
     * Hasil dari broadcast function adalah BroadcastChannel
     */
    @Test
    fun testBroadcastFunction() {
        val scope = CoroutineScope(Dispatchers.IO)
        val broadcastChannel = scope.broadcast<Int>(capacity = 10) {
            repeat(10) { send(it) }
        }
        val receiver1 = broadcastChannel.openSubscription()
        val receiver2 = broadcastChannel.openSubscription()
        runBlocking {
            scope.launch {
                repeat(10) { println("Receiver 1: ${receiver1.receive()}") }
            }.join()
            scope.launch {
                println()
                repeat(10) { println("Receiver 2: ${receiver2.receive()}") }
            }.join()
        }
    }
}
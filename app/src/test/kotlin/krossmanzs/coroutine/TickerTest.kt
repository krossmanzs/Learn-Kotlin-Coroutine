package krossmanzs.coroutine

import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import java.util.Date

class TickerTest {
    /**
     * Ticker Function
     * adalah function yang bisa kita gunakan untuk membuat channel
     * mirip dengan timer.
     *
     * Dengan ticker, kita bisa menentukan sebuah pesan akan dikirim
     * dalam waktu timer yang sudah kita tentukan.
     *
     * Ini cocok jika kita ingin membuat timer menggunakan coroutine
     * dan channel
     *
     * Return value dari ticker function adalah ReceiveChannel<Unit>,
     * dan setiap kita receive data, datanya hanya berupa data null.
     */
    @Test
    fun testTickerFunction() {
        runBlocking {
            val receiveChannel = ticker(delayMillis = 1_000)
            repeat(10) {
                receiveChannel.receive()
                println(Date())
            }
            receiveChannel.cancel()
        }
    }
}
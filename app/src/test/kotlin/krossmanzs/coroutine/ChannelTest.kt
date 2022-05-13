package krossmanzs.coroutine

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import org.junit.jupiter.api.Test
import java.util.concurrent.Executors

/**
 * Channel
 * adalah fitur di Kotlin Coroutine yang bisa digunakan
 * untuk mentransfer aliran data dari satu tempat ke tempat
 * lain.
 *
 * Channel mirip struktur data queue, dimana ada data masuk dan
 * ada data keluar.
 *
 * Untuk mengirim data ke channel, kita bisa menggunakan function
 * send() dan untuk mengambil data di channel, kita bisa menggunakan
 * function receive().
 *
 * Channel itu sifatnya blocking, artinya jika tidak ada data di
 * channel, saat kita mengambil data menggunakan receive() maka
 * dia akan menunggu sampai ada data. Dan begitu juga ketika ada
 * data di channel, dan tidak ada yang mengambilnya, saat kita send()
 * data, dia akan menunggu sampai channel kosong(datanya diambil)
 *
 * Untuk menutup channel, kita bisa menggunakan function close()
 */

class ChannelTest {
    @Test
    fun testChannel() {
        runBlocking {
            val channel = Channel<Int>()
            val job2 = launch{
                println("Send 1")
                channel.send(1)
                println("Send 2")
                channel.send(2)
            }

            val job1 =launch {
                println("Receive1 ${channel.receive()}")
                println("Receive2 ${channel.receive()}")
            }

            joinAll(job1,job2)
            channel.close()
        }
    }

    /**
     * Channel Backpressure / Channel Buffer
     *
     * Secara default, channel hanya bisa menampung satu data,
     * artinya jika kita mencoba mengirim data lain ke channel,
     * maka kita harus menunggu data yang ada diambil.
     *
     * Namun kita bisa menambahkan buffer di dalam channel atau
     * istilahnya capacity. Jadi defaultnya capacity nya adalah
     * 0 (buffer atau antrian yang bisa ditampung)
     *
     * Contoh Constant Channel Capacity
     *
     * Channel.UNLIMITED
     * berkapasitas int.MAX_VALUE
     *
     * Channel.CONFLATED
     * berkapasitas -1
     *
     * Channel.RENDEZVOUS
     * berkapasitas 0
     *
     * Channel.BUFFERED
     * berkapasitas 64 atau bisa di setting via properties
     */
    @Test
    fun testChannelUnlimited() {
        runBlocking {
            val channel = Channel<Int>(capacity = Channel.UNLIMITED)
            val scope = CoroutineScope(Dispatchers.IO)
            val job1 = scope.launch {
                repeat(10) {
                    println("Send $it")
                    channel.send(it)
                }
            }

            val job2 = scope.launch {
//                println("Receive 1 ${channel.receive()}")
//                println("Receive 2 ${channel.receive()}")
            }

            joinAll(job1,job2)
            channel.close()
        }
    }

    @Test
    fun testChannelConflated() {
        /*
        Conflated(-1) artinya saat ngirim data abis itu kriim data lagi
        ternyata data pertama belum diterima oleh coroutine yang lain
        maka data yang pertama itu akan dihapus dari channel
         */

        runBlocking {
            val channel = Channel<Int>(capacity = Channel.CONFLATED)
            val scope = CoroutineScope(Dispatchers.IO)
            val job1 = scope.launch {
                println("Send 1")
                channel.send(1)
                println("Send 2")
                channel.send(2)
            }

            job1.join()

            val job2 = scope.launch {
                println("Receive 1 ${channel.receive()}")
            }

            job2.join()

            channel.close()
        }
    }

    /**
     * Channel Buffer Overflow
     *
     * Ada kalahnya buffer sudah penuh, dan sender tetap
     * mengirimkan data.
     *
     * Dalam kasus ini, kita bisa mengatur ketika terjadi buffer
     * overflow(kelebihan data yang ditampung oleh buffer), kita
     * bisa menggunakan enum BufferOverFlow:
     *
     * SUSPEND
     * Block sender (nunggu sampai buffernya kosong)
     *
     * DROP_OLDEST
     * Hapus data di buffer yang paling lama (paling depan)
     *
     * DROP_LATEST
     * Hapus data di buffer yang paling baru (paling belakang)
     */
    @Test
    fun testChannelOverflow() {
        runBlocking {
            val channel = Channel<Int>(capacity = 5, onBufferOverflow = BufferOverflow.DROP_OLDEST)
            val scope = CoroutineScope(Dispatchers.IO)
            val job1 = scope.launch {
                repeat(10) {
                    channel.send(it)
                }
            }
            job1.join()
            delay(1_000)
            val job2 = launch {
                repeat(5) {
                    println("Receive-$it = ${channel.receive()}")
                }
            }
            job2.join()
            channel.close()
        }
    }

    /**
     * Channel Undelivered Element
     *
     * Jika ada kasus dimana sebuah channel sudah di close, tetapi ada
     * coroutine yang masih mencoba mengirim data ke channel.
     *
     * Ketika kita mencoba mengirim data ke channel yang sudah di close,
     * maka secara otomatis channel akan mengembalikan error ClosedSendChannelException
     *
     * Lalu bagaimana dengan data yang sudah dikirim?
     * Kita bisa menambah lambda function ketika membuat channel, sebagai fallback
     * ketika sebuah data dikirim dan channel sudah di close, maka fallback tersebut
     * akan dieksekusi
     *
     * Function fallback tersebut bernama onUndeliveredElement
     */
    @Test
    fun testUndeliveredElement() {
        runBlocking {
            val channel = Channel<Int>(capacity = 5) { value ->
                println("Undelivered value $value")
            }
            val dispatcher = Executors.newFixedThreadPool(10).asCoroutineDispatcher()
            val scope = CoroutineScope(dispatcher)
            channel.close()
            val job = scope.launch {
                channel.send(10)
                channel.send(200)
            }
            job.join()
        }
    }
}
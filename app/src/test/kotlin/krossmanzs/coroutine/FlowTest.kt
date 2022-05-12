package krossmanzs.coroutine

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

class FlowTest {
    /**
     * Asynchronous Flow
     *
     * Bagaimana jika kita butuh sebuah coroutine yang mengembalikan
     * data berkali-kali seperti layaknya collection?
     *
     * Kotlin mendukung hal tersebut dengan nama Flow. Flow mirip
     * dengan sequence di Kotlin Collection, yang membedakan adalah
     * flow berjalan sebagai coroutine dan kita bisa menggunakan
     * suspend function di flow.
     *
     * Flow adalah collection cold atau lazy, artinya jika tidak diminta
     * datanya, flow tidak akan berjalan (kode nya tidak akan dieksekusi)
     *
     * Untuk membuat flow, kita bisa menggunakan function flow()
     *
     * Di dalam flow, untuk mengirim data kita bisa menggunakan function emit()
     *
     * Untuk mengakses data yang ada di flow, kita bisa menggunakan function
     * collect()
     */
    @Test
    fun testAsynchronousFlow() {
        val flow: Flow<Int> = flow {
            println("Flow started")
            repeat(100) {
                println("Emit $it")
                emit(it)
            }
        }

        runBlocking {
            flow.collect() {
                println("$it")
            }
        }
    }
}
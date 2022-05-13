package krossmanzs.coroutine

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
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

    /**
     * Flow Operator
     *
     * Flow mirip dengan Kotlin Collection, memiliki
     * banyak operator.
     *
     * Hampir semua operator yang ada di Kotlin Collection ada
     * juga di Flow, seperti map, flatMap, filter, reduce, dll
     *
     * Yang membedakan dengan operator yang ada di Kotlin Collection
     * adalah, operator di Flow mendukung suspend function.
     */
    suspend fun numberFlow() : Flow<Int> = flow {
        repeat(100) {
            emit(it)
        }
    }

    suspend fun changeToString(number: Int): String {
        delay(100)
        return "Number $number"
    }

    @Test
    fun testFlowOperator() {
        runBlocking {
            val flow1 = numberFlow()
            flow1.filter { it % 2 == 0 }
                .map { changeToString(it) }
                .collect { println(it) }
        }
    }
}
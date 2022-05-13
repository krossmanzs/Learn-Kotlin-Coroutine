package krossmanzs.coroutine

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
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

    /**
     * Flow Exception
     *
     * Saat terjadi exception pada flow, di bagian operator
     * apapun, maka flow akan berhenti, lalu exception akan
     * di throw oleh flow.
     *
     * Untuk menangkap exception tersebut, kita bisa menggunakan
     * block try-catch.
     *
     * Namun flow juga menyediakan operator untuk menangkap exception
     * tersebut, nama functionnya adalah catch()
     *
     * Untuk finally, flow menggunakan onCompletion()
     *
     * Jika terjadi error di flow, flow akan dihentikan, jika kita ingin
     * flow tidak berhenti saat terjadi error, pastikan kita selalu
     * melakukan try catch di kode flow nya.
     */
    @Test
    fun testFlowException() {
        runBlocking {
            val flow = numberFlow()
            flow.map { check(it < 10); it }
                .onEach { println(it) }
                .catch { println("Error ${it.message}") }
                .onCompletion { println("Done || Finally") }
                .collect()
        }
    }

    /**
     * Cancellable Flow
     *
     * Flow adalah coroutine, artinya dia bisa dibatalkan.
     *
     * Untuk membatalkan flow, kita bisa menggunakan function cancel()
     * milik coroutine scope, function cancel() tersebut akan secara
     * otomatis membatalkan job coroutine
     */
    @Test
    fun testCancellableFlow() {
        runBlocking {
            val flow = numberFlow()
            val scope = CoroutineScope(Dispatchers.IO)
            scope.launch {
                flow.onEach {
                    if(it > 10) cancel()
                    else println("Number $it in ${Thread.currentThread().name}")
                }.collect()
            }.join()
        }
    }
}
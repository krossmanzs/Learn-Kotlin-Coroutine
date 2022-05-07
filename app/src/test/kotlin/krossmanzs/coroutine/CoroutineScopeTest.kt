package krossmanzs.coroutine

import kotlinx.coroutines.*
import org.junit.jupiter.api.Test

    /*
        Coroutine Scope

        GlobalScope sebenarnya adalah salah satu
        implementasi Coroutine Scope

        Semua coroutine dijalankan dari sebuah Coroutine Scope

        Function launch dan async sebenarnya adalah extention function
        dari Coroutine Scope

        Sederhananya, Coroutine Scope adalah life cyclenya coroutine
     */

    /*
        Penggunaan

        Orang orang biasanya menggunakan CoroutineScope sebagai
        satu kesatuan flow.

        Misal saat kita membuka sebuah halaman di mobile, maka kita akan
        membuat screen, lalu mengambil data ke server, lalu setelah
        mendapatkannya kita akan menampilkan data tersebut di screen.

        Flow tersebut harus terintegrasi, jika misal flow tersebut sukses maka
        harus sukses semua. Jika dibatalkan, maka harus dibatalkan proses selanjutnya

        Hal itu diibaratkan sebagai tiap aktivitas adalah coroutine, maka flow tersebut
        disimpan dalam sebuah coroutine scope
     */

    /*
        GlobalScope

        penggunaan GlobalScope tidak dianjurkan dalam pembuatan aplikasi

        Karena jika semua coroutine menggunakan GlobalScope, maka secara otomatis
        akan sharing coroutine scope, hal ini agak menyulitkan saat kita misal ingin
        membatalkan sebuah flow, karena saat sebuah coroutine scope di batalkan, maka
        semua coroutine yang terdapat di scope tersebut akan dibatalkan
     */

class CoroutineScopeTest {
    @Test
    fun testCoroutineScope() {
        val scope = CoroutineScope(Dispatchers.Default)
        scope.launch {
            delay(2_000)
            println("Run ${Thread.currentThread().name}")
        }

        scope.launch {
            delay(2_000)
            println("Run ${Thread.currentThread().name}")
        }

        runBlocking {
            delay(1_000)
            scope.cancel()
            delay(2_000)
            println("Done")
        }
    }
}
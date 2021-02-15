package com.app.floo

import org.junit.Test
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun convertStringToTimeStamp() {
        val currentEpochTime = Instant.now().epochSecond
        val dateNow = DateTimeFormatter.ISO_INSTANT.format(Instant.ofEpochSecond(currentEpochTime))
        println(currentEpochTime)
        println(dateNow)
        val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
        val date = Date(currentEpochTime * 1000)
        val result = formatter.format(date)
        println(result)
    }

}
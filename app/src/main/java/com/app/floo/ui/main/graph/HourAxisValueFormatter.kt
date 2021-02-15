package com.app.floo.ui.main.graph

import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.text.SimpleDateFormat
import java.util.*

/**
 * This is workaround due to limitation of MPAndroidChart
 * You can see the reference from link below
 *
 * https://github.com/PhilJay/MPAndroidChart/issues/789
 */

class HourAxisValueFormatter constructor(
    private val mDateFormat: SimpleDateFormat = SimpleDateFormat("HH:mm", Locale.getDefault()),
    private val mDate: Date = Date()
): IndexAxisValueFormatter() {

    // referenceEpochTimestamp is the time when receiving data for the first time
    private var referenceEpochTimestamp: Long = 0L

    fun setReferenceEpochTimestamp(epochTimeStamp: Long){
        this.referenceEpochTimestamp = epochTimeStamp
    }

    override fun getFormattedValue(value: Float): String = getFormattedHour(value)

    fun getFormattedHour(value: Float): String {
        // convertedEpochTimestamp = originalTimestamp or value - referenceTimestamp
        val convertedEpochTimestamp = value.toLong()

        // Retrieve actual epoch timestamp
        val actualTimestamp: Long = referenceEpochTimestamp + convertedEpochTimestamp

        // Convert epoch timestamp to hour:minute
        return getHour(actualTimestamp)
    }

    private fun getHour(epochTimeStamp: Long) : String {
        return try {
            mDate.time = epochTimeStamp * 1000
            mDateFormat.format(mDate)
        } catch (ex: Exception) {
            "xx:xx"
        }
    }

}
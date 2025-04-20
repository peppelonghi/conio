package com.giuseppe_longhitano.ui.view.widget.chart

import android.annotation.SuppressLint
import com.github.mikephil.charting.formatter.ValueFormatter
import com.giuseppe_longhitano.arch.model.ScaleInfo
import com.giuseppe_longhitano.arch.utils.Utils.Companion.formatValue
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

private const val TAG = "IntervalTimestampXAxisV"

class IntervalFormatter(
    private val timestampMap: Map<Float, Double>,
    private val interval: Interval
) : ValueFormatter() {
    override fun getFormattedValue(value: Float): String =
        convertTimestampToDateTime(timestampMap[value] ?: 0.0, Interval.retrieveFormat(interval))

    @SuppressLint("SimpleDateFormat")
    fun convertTimestampToDateTime(
        timestamp: Double,
        formatValue: String = "dd/MM/yyyy HH:mm"
    ): String {
        if (timestamp <= 0) {
            return "" // Or handle invalid timestamps differently, e.g., throw an exception
        }
        val date = Date(timestamp.toLong())
        val format = SimpleDateFormat(formatValue)
        format.timeZone = TimeZone.getDefault()
        return format.format(date)
    }


}


//sto considerando solo l euro come vs_currency
class ScaleFormatterEur(val scale: ScaleInfo) : ValueFormatter() {
    //
    override fun getFormattedValue(value: Float): String = formatValue(value, scale) + " €"
}
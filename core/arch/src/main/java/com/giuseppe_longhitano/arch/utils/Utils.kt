package com.giuseppe_longhitano.arch.utils

import com.giuseppe_longhitano.arch.model.ScaleInfo
import java.math.BigDecimal
import java.math.RoundingMode

class Utils {
    companion object{

        fun formatValue(value: Float, scaleInfo: ScaleInfo): String {
            val rounded = roundToTwoDecimalsBigDecimal(value.toDouble())
            return "$rounded${scaleInfo.suffix}"
        }

        fun roundToTwoDecimalsBigDecimal(value: Double): Double {
            return BigDecimal(value).setScale(2, RoundingMode.HALF_UP).toDouble()
        }
    }
}
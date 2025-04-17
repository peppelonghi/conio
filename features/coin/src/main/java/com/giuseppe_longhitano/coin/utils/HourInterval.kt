package com.giuseppe_longhitano.coin.utils

enum class HourInterval(val value: String) {
    FIFTEEN_MINUTES("15m"),
    THIRTY_MINUTES("30m"),
    ONE_HOUR("1h"),
    TWO_HOURS("2h"),
    SIX_HOURS("6h"),
    TWELVE_HOURS("12h"),
    ONE_DAY("1d");

    companion object {
        fun safeValueOf(value: String? = null): HourInterval {
            return when (value) {
                "15m" -> FIFTEEN_MINUTES
                "30m" -> THIRTY_MINUTES
                "1h" -> ONE_HOUR
                "2h" -> TWO_HOURS
                "6h" -> SIX_HOURS
                "12h" -> TWELVE_HOURS
                "1d" -> ONE_DAY
                else -> THIRTY_MINUTES
            }
        }
    }
}
package com.giuseppe_longhitano.features.coins.utils

enum class DayInterval(val value: String) {
    ONE_DAY("1"),
    SEVEN_DAYS("7"),
    FOURTEEN_DAYS("14"),
    THIRTY_DAYS("30"),
    NINETY_DAYS("90"),
    ONE_HUNDRED_EIGHTY_DAYS("180"),
    THREE_HUNDRED_SIXTY_FIVE_DAYS("365"),
    MAX("max");

    companion object {
        fun safeValueOf(value: String?=null): DayInterval {
            return when (value) {
                "1" -> ONE_DAY
                "7" -> SEVEN_DAYS
                "14" -> FOURTEEN_DAYS
                "30" -> THIRTY_DAYS
                "90" -> NINETY_DAYS
                "180" -> ONE_HUNDRED_EIGHTY_DAYS
                "365" -> THREE_HUNDRED_SIXTY_FIVE_DAYS
                "max" -> MAX
                else -> ONE_DAY
            }
        }
    }
}
package com.giuseppe_longhitano.ui.view.widget.chart

enum class Interval(val value: String) {

    TWENTY_FOUR_HOURS("1"),
    SEVEN_DAYS("7"),
    ONE_MONTH("30"),
    THREE_MONTHS("90"),
    ONE_YEAR("365");


    companion object {
        fun safeValueOf(value: String? = null): Interval {
            return when (value) {
                TWENTY_FOUR_HOURS.value -> TWENTY_FOUR_HOURS
                SEVEN_DAYS.value -> SEVEN_DAYS
                ONE_MONTH.value -> ONE_MONTH
                THREE_MONTHS.value -> THREE_MONTHS
                ONE_YEAR.value -> ONE_YEAR

                else -> TWENTY_FOUR_HOURS
            }
        }

        fun retrieveFormat(interval: Interval): String = when (interval) {
            TWENTY_FOUR_HOURS -> "HH:mm" //  (e.g., Mar 10 14:00)
            SEVEN_DAYS, ONE_MONTH, THREE_MONTHS,ONE_YEAR -> "dd.MM" // Month and Day (e.g., Mar 10)

        }

    }
}
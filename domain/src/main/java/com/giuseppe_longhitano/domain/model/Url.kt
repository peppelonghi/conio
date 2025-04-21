package com.giuseppe_longhitano.domain.model

sealed class Url {
    abstract val value: String

    data class HttpUrl(override val value: String) : Url()

    data class NotValidUrl(override val value: String = "") : Url()

    companion object {
        private fun isValidUrl(url: String?) =
            !url.isNullOrBlank() && (url.startsWith("http://") || url.startsWith("https://"))

        fun createUrl(url: String?): Url =
            if (isValidUrl(url)) HttpUrl(url.orEmpty())
            else NotValidUrl(url.orEmpty())
    }
}
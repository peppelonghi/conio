package com.giuseppe_longhitano.network.model

import kotlinx.serialization.Serializable


@Serializable
data class ImagesDTO(val thumb: String, val small: String, val large: String)
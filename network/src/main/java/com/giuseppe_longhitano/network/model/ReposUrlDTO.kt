package com.giuseppe_longhitano.network.model

import kotlinx.serialization.Serializable

@Serializable
data class ReposUrlDTO(
    val github: List<String>,
    val bitbucket: List<String>
)
package com.giuseppe_longhitano.arch.event

sealed class CommonEvent: UIEvent {
    data object Retry: UIEvent
    data object Next: UIEvent
}
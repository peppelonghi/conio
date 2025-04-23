package com.giuseppe_longhitano.ui.view.shared.common_event

import com.giuseppe_longhitano.arch.event.UIEvent

data class SelectionEvent<T>(val model: T) : UIEvent
package com.giuseppe_longhitano.ui.view.widget.drop_down

import com.giuseppe_longhitano.arch.event.UIEvent

data class DropDownEvent<T>(val model: T) : UIEvent
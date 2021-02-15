package com.app.floo.extensions

import android.graphics.Color
import android.widget.TextView

fun TextView.setTextColor(hexColor: String) {
    this.setTextColor(Color.parseColor(hexColor))
}
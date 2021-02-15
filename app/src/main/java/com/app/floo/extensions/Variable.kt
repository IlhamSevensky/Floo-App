package com.app.floo.extensions

import java.util.*

fun String.toLowerCase() = this.toLowerCase(Locale.getDefault())

fun String.asUnit(unitType: String = "cm"): String = this.trim().plus(" $unitType")

fun getEpochTimestamp(): Long = System.currentTimeMillis() / 1000
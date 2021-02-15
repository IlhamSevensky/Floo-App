package com.app.floo.ui.main

enum class StatusState(val value: String, val hexColor: String) {
    DANGER(value = "bahaya", hexColor = "#E53935"),
    WARNING(value = "siaga", hexColor = "#FDD835"),
    SAFE(value = "aman", hexColor = "#43A047"),
}
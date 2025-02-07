package com.example.main.model

fun randomTitle(): String {
    val titles = listOf(
        "Einkaufsliste schreiben",
        "Wohnung aufräumen",
        "Zahnarzttermin vereinbaren",
        "Geburtstagsgeschenk besorgen",
    )

    return titles.random()
}

fun randomSubject(): String {
    val subjects = listOf(
        "Fang einfach an!",
        "Kleine Schritte führen auch zum Ziel.",
        "Du schaffst das!",
        "Nicht aufgeben!",
    )
    return subjects.random()
}
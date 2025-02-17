package com.example.main.model

import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.random.Random


val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
val timeFormatter = DateTimeFormatter.ofPattern("HH:mm \'Uhr\'")
val dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm 'Uhr'")

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

fun randomDateString(): String {
    val now = LocalDateTime.now()
    val maxDate = now.plusMonths(3L)
    val randomDays = Random.nextInt(maxDate.dayOfYear - now.dayOfYear + 1)
    val randomDate = now.plusDays(randomDays.toLong())
    return randomDate.format(dateFormatter)
}

fun randomTimeString(): String {
    val now = LocalDateTime.now()
    val randomTime = now.plusHours(Random.nextLong(0L, 24L))
    return randomTime.format(timeFormatter)
}

fun dateStringToEpochMillis(dateString: String): Long {
    val presetDate = LocalDate.parse(dateString, dateFormatter).atStartOfDay(ZoneId.of("Europe/Berlin"))
    return presetDate.toInstant().toEpochMilli() + 12*60*60*1000L
}

@OptIn(ExperimentalMaterial3Api::class)
fun datePickerStateToDateString(datePickerState: DatePickerState): String {
    val date =  Instant.ofEpochMilli(datePickerState.selectedDateMillis ?: 0)
        .atZone(ZoneId.systemDefault()).toLocalDate()
    return date.format(dateFormatter)
}

fun timeStringToLocalTime(timeString: String): LocalTime {
    return LocalTime.parse(timeString, timeFormatter)
}

@OptIn(ExperimentalMaterial3Api::class)
fun timePickerStateToTimeString(timePickerState: TimePickerState): String {
    val time = LocalTime.of(timePickerState.hour, timePickerState.minute, 0)
    return time.format(timeFormatter)
}

fun timeDiffInSeconds(dateString: String, timeString: String): Long {
    val dateTimeString = "$dateString $timeString"
    val time = LocalDateTime.parse(dateTimeString, dateTimeFormatter)
    return Duration.between(LocalDateTime.now(), time).seconds
}

fun secondsToDayHourMinSecString(seconds: Long): String {
    val days = seconds / 86400
    val hours = (seconds - days*86400)  /  3600
    val minutes = (seconds - days*86400 - hours*3600) /  60
    val secs = seconds %  60
    return String.format("%02d Tage\n%02d Stunden\n%02d Minuten\n%02d Sekunden", days, hours, minutes, secs)
}
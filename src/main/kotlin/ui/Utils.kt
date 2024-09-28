package ui

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

fun isValidDate(date: String): Boolean {
    // Шаблон для проверки формата yyyy-MM-dd
    val regex = Regex("""\d{4}-\d{2}-\d{2}""")

    // Проверяем, соответствует ли строка шаблону
    if (!regex.matches(date)) {
        return false
    }

    // Дополнительно проверяем, является ли строка корректной датой
    return try {
        LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        true
    } catch (e: DateTimeParseException) {
        false
    }
}

fun subtractDates(date1: String, date2: String): Boolean {
    // Проверяем, валидны ли даты
    if (!isValidDate(date1) || !isValidDate(date2)) {
        return false
    }
    println("${!isValidDate(date1) || !isValidDate(date2)}")


        val localDate1 = LocalDate.parse(date1)
        val localDate2 = LocalDate.parse(date2)

        // Вычисляем разницу в днях
        val daysDifference = localDate1.toEpochDay() - localDate2.toEpochDay()

    println("daysDifference >= 0: ${daysDifference >= 0}")
        return daysDifference >= 0

}

package com.anadolstudio.utils.util.extentions

fun Float.shortFormat(): String = String.format("%.1f", this).replace("[.,]0".toRegex(), "")

private const val ELLIPSIS = "…"

fun String.ellipsize(maxLength: Int): String = if (length > maxLength) {
    take(maxLength - 1).trim().plus(ELLIPSIS)
} else {
    this
}

fun String.ifNotEmpty(action: () -> String): String = if (isNotEmpty()) plus(action.invoke()) else this

fun String.nullIfEmpty(): String? = ifEmpty { null }

fun String.getAllFirstIndexesByQuery(query: String, ignoreRegister: Boolean = false): List<Int> {
    if (query.isEmpty()) return emptyList()

    val correctQuery = if (ignoreRegister) query.lowercase() else query

    var currentQueryCharIndex = 0
    val firstIndexes = mutableListOf<Int>()

    forEachIndexed { index, char ->
        val correctChar = if (ignoreRegister) char.lowercaseChar() else char
        val containChar = correctChar == correctQuery[currentQueryCharIndex]

        when {
            containChar && currentQueryCharIndex == correctQuery.lastIndex -> {
                firstIndexes.add(index - currentQueryCharIndex)
            }

            containChar -> currentQueryCharIndex++
            else -> currentQueryCharIndex = 0
        }
    }

    return firstIndexes
}

fun String.getAllFirstAndLastIndexesByQuery(
        query: String,
        ignoreCase: Boolean = false
): List<Pair<Int, Int>> = getAllFirstIndexesByQuery(query, ignoreCase).map { firstIndex ->
    Pair(firstIndex, firstIndex + query.length)
}

fun String.getWords(): List<String> = split(
        Regex("[^А-Яа-яA-Za-z0-9]")
).filter { it.isNotBlank() }

fun String.getWordsWithSimilarSimilar(): List<String> = split(Regex("[^А-Яа-яA-Za-z0-9]"))
        .filter { it.isNotBlank() }
        .flatMap { word ->
            when {
                word.length > 7 -> listOf(word) + word.dropLast(3)
                word.length > 5 -> listOf(word) + word.dropLast(1)
                else -> listOf(word)
            }
        }

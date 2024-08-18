package com.anadolstudio.utils.util.extentions

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

fun String.getAllFirstAndLastIndexesByQuery(query: String, ignoreCase: Boolean = false): List<Pair<Int, Int>> {
    return getAllFirstIndexesByQuery(query, ignoreCase)
            .map { firstIndex ->
                Pair(firstIndex, firstIndex + query.length)
            }
}

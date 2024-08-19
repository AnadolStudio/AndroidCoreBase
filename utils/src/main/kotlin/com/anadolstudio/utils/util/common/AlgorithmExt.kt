package com.anadolstudio.utils.util.common

fun <T : Any?> Collection<T>.getMapWithNumberOfRepetitions(): Map<T, Int> {
    val result = mutableMapOf<T, Int>()

    forEach {
        val previousCount = result[it] ?: 0
        result[it] = previousCount + 1
    }

    return result
}

fun <T : Any?> Collection<T>.removeDuplicateAndSortedByRepetition(): List<T> {
    val map = getMapWithNumberOfRepetitions()

    return map.keys.sortedByDescending { value -> map[value] }
}

fun <ListType : Any?> List<ListType>.removeDuplicateAndSortedByNumberOfRepetitions(
        queryList: Set<String>,
        mapper: (ListType) -> String
): List<ListType> {
    val map = mutableMapOf<ListType, Int>()

    forEach {
        map[it] = 0
    }

    map.keys.forEach {
        val mappedData = mapper.invoke(it)

        queryList.forEach { key ->
            if (mappedData.contains(key)) {
                val previousCount = map[it] ?: 0
                map[it] = previousCount + 1
            }
        }
    }

    return map.keys.sortedByDescending { value -> map[value] }
}

package com.anadolstudio.ui

import com.anadolstudio.utils.util.common.getMapWithNumberOfRepetitions
import com.anadolstudio.utils.util.common.removeDuplicateAndSortedByRepetition
import org.junit.Assert
import org.junit.Test

class AlgorithmTest {

    @Test
    fun `number of repetitions with null-- is correct`() {
        val query = listOf(
                "Бра",
                "Лала",
                "Бра",
                "Бра",
                "Лала",
                "777",
                "777",
                null,
                "777",
                "777",
                null
        )

        val map = query.getMapWithNumberOfRepetitions()

        Assert.assertEquals(
                mapOf(
                        "Бра" to 3,
                        "Лала" to 2,
                        "777" to 4,
                        null to 2,
                ),
                map
        )
    }

    @Test
    fun `number of repetitions -- is correct`() {
        val query = listOf(
                "Бра",
                "Лала",
                "Бра",
                "Бра",
                "Лала",
                "777",
                "777",
                "777",
                "777",
        )

        val map = query.getMapWithNumberOfRepetitions()

        Assert.assertEquals(
                mapOf(
                        "Бра" to 3,
                        "Лала" to 2,
                        "777" to 4,
                ),
                map
        )
    }

    @Test
    fun `sorted list by number of repetitions -- is correct`() {
        val query = listOf(
                "Бра",
                "Лала",
                "Бра",
                "Бра",
                "Лала",
                "777",
                "777",
                "777",
                "777",
        )

        val result = query.removeDuplicateAndSortedByRepetition()

        Assert.assertEquals(listOf("777", "Бра", "Лала"), result)
    }

    @Test
    fun `sorted list by number of repetitions with keys-- is correct`() {
        val query = listOf(
                "Бра",
                "Лала",
                "Бра",
                "Бра",
                "Лала",
                "777",
                "777",
                "777",
                "777",
        )

        val result = query.removeDuplicateAndSortedByRepetition()

        Assert.assertEquals(listOf("777", "Бра", "Лала"), result)
    }

}

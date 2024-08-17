package com.anadolstudio.ui

import com.anadolstudio.utils.util.extentions.getAllFirstIndexesByQuery
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class StringTest {

    private companion object {
        const val TEXT = "Кобра добра, ваниль, тесто, сомбра, БРА"
    }

    @Test
    fun `empty case`() {
        val query = ""
        val indexes = TEXT.getAllFirstIndexesByQuery(query)

        assertEquals(indexes, emptyList<Int>())
    }

    @Test
    fun `find all indexes by query without ignore register -- is correct`() {
        val query = "бра"
        val indexes = TEXT.getAllFirstIndexesByQuery(query)

        assertEquals(indexes, listOf(2, 8, 31))
    }

    @Test
    fun `find all indexes by query without ignore register -- is not correct`() {
        val query = "бра"
        val indexes = TEXT.getAllFirstIndexesByQuery(query)

        assertNotEquals(indexes, listOf(1))
    }

    @Test
    fun `find all indexes by query with ignore register -- is correct`() {
        val query = "Бра"

        val indexes = TEXT.getAllFirstIndexesByQuery(query, ignoreRegister = true)

        assertEquals(indexes, listOf(2, 8, 31, 36))
    }
}

package com.anadolstudio.ui

import com.anadolstudio.utils.util.extentions.getAllFirstIndexesByQuery
import com.anadolstudio.utils.util.extentions.getWords
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

        assertEquals(emptyList<Int>(), indexes)
    }

    @Test
    fun `find all indexes by query without ignore register -- is correct`() {
        val query = "бра"
        val indexes = TEXT.getAllFirstIndexesByQuery(query)

        assertEquals(listOf(2, 8, 31), indexes)
    }

    @Test
    fun `find all indexes by query without ignore register -- is not correct`() {
        val query = "бра"
        val indexes = TEXT.getAllFirstIndexesByQuery(query)

        assertNotEquals(listOf(1), indexes)
    }

    @Test
    fun `find all indexes by query with ignore register -- is correct`() {
        val query = "Бра"

        val indexes = TEXT.getAllFirstIndexesByQuery(query, ignoreRegister = true)

        assertEquals(listOf(2, 8, 31, 36), indexes)
    }

    @Test
    fun `find words -- is correct`() {
        val query = "Бра, Bra - Бра1224!Low _"
        val words = query.getWords()

        assertEquals(listOf("Бра", "Bra", "Бра1224", "Low"), words)
    }

}

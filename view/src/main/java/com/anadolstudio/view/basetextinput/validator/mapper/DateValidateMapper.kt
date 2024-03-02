package com.anadolstudio.view.basetextinput.validator.mapper

import com.anadolstudio.utils.util.data_time.safeParseDateTime
import org.joda.time.DateTime

class DateValidateMapper : ValidateMapper {

    private companion object {

        val INVALID_DATE = DateTime.now().minusDays(1).millis
    }

    override fun map(text: String): String = getMillsFromDateShort(text)

    private fun getMillsFromDateShort(text: String): String {
        val millis = text.safeParseDateTime()
                ?.millis
                ?: INVALID_DATE

        return millis.toString()
    }

}

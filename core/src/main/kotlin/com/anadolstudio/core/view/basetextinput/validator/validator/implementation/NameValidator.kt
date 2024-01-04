package com.anadolstudio.core.view.basetextinput.validator.validator.implementation

import androidx.annotation.StringRes

class NameValidator(
        @StringRes errorMessage: Int,
) : SimpleValidator(
        condition = { name ->
            NAME_PATTERN.matches(name) && !MISMATCH_PATTERN.containsMatchIn(name)
        },
        errorMessage = errorMessage,
) {
    private companion object {
        // "\u0020" - пробел
        const val BASE_SYMBOLS = "а-яА-ЯёЁ"

        val NAME_PATTERN = "^[$BASE_SYMBOLS][\\u0020\\-$BASE_SYMBOLS]*[$BASE_SYMBOLS]\$".toRegex()

        // TODO придумать как включить всё это в одну регулярку
        // проверка на пробелы и дефисы
        val MISMATCH_PATTERN = "\\u0020\\u0020|\\u0020-|-\\u0020|--".toRegex()
    }
}

package com.anadolstudio.core.view.basetextinput.validator.validator.implementation

import androidx.annotation.StringRes
import java.util.regex.Pattern

class DateFormatValidator(
        @StringRes errorMessage: Int,
) : PatternValidator(
        pattern = Pattern.compile("$DAY_PATTERN$MONTH_PATTERN$YEAR_PATTERN"),
        errorMessage = errorMessage,
) {
    private companion object {
        // [0-3] as first char - correct
        // (0)[1-9] is [01; 09] range, excluding "00"
        // [1-2][0-9] is [10; 20] range
        // (3)[0-1] is [30;31] range
        const val DAY_PATTERN = "[0-3]|((0)[1-9]|[1-2][0-9]|(3)[0-1])(.)"

        // ^$ empty string - correct
        // [0-1] as first char - correct
        // (0)[1-9] is [01; 09] range, excluding "00"
        // (1)[0-2] is [10; 12] range
        const val MONTH_PATTERN = "[0-1]|((0)[1-9]|(1)[0-2])(.)"

        // [1-2] as first char - correct
        // (19)|(20) is [19; 20] range
        // (19)[0-9] is [190; 199] range
        // (19)[0-9][0-9] is [1900; 1999] range
        // (20)[0-9] is [200; 209] range
        // (20)[0-9][0-9] is [2000; 2099] range
        const val YEAR_PATTERN = "[1-2]|(19)|(20)|(19)[0-9]|(19)[0-9][0-9]|(20)[0-9]|(20)[0-9][0-9]"
    }
}

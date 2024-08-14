package com.anadolstudio.analytics.dsl

import android.os.Bundle
import androidx.annotation.StringRes
import com.anadolstudio.analytics.R

class ParametersBuilder(
        private val getString: (value: Int, formatArgs: Array<out Any>) -> String
) {
    val bundle: Bundle = Bundle()

    @AnalyticDSL
    fun param(key: String, value: String?) = bundle.putString(key, value)

    @AnalyticDSL
    fun param(key: String, value: Bundle?) = bundle.putBundle(key, value)

    @AnalyticDSL
    fun param(@StringRes key: Int, value: String?) = param(getString(key, arrayOf()), value)

    @AnalyticDSL
    fun param(
            @StringRes key: Int,
            @StringRes value: Int,
            vararg formatArgs: Any
    ) = param(getString(key, arrayOf()), getString(value, formatArgs))

    @AnalyticDSL
    fun screenName(
            @StringRes value: Int,
            vararg formatArgs: Any
    ) = param(R.string.analytics_screen_name, getString(value, formatArgs))

    @AnalyticDSL
    fun itemName(
            @StringRes value: Int,
            vararg formatArgs: Any
    ) = param(R.string.analytics_item_name, getString(value, formatArgs))

    @AnalyticDSL
    fun screenName(value: String) = param(R.string.analytics_screen_name, value)

    @AnalyticDSL
    fun itemName(value: String) = param(R.string.analytics_item_name, value)
}

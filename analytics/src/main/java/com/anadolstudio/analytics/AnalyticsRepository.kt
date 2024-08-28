package com.anadolstudio.analytics

import androidx.annotation.StringRes
import com.anadolstudio.analytics.dsl.AnalyticDSL
import com.anadolstudio.analytics.dsl.ParametersBuilder

@AnalyticDSL
interface AnalyticsRepository {

    @AnalyticDSL
    fun event(
            eventName: String,
            scope: ParametersBuilder.() -> Unit = {},
    )

    @AnalyticDSL
    fun event(
            @StringRes eventNameId: Int,
            scope: ParametersBuilder.() -> Unit = {},
    )

    @AnalyticDSL
    fun screenViewEvent(
            builder: ParametersBuilder.() -> Unit,
    ) = event(R.string.analytics_screen_view) {
        builder()
    }

    @AnalyticDSL
    fun screenViewEvent(
            name: String,
            builder: ParametersBuilder.() -> Unit = {},
    ) = event(R.string.analytics_screen_view) {
        screenName(name)
        builder()
    }

    @AnalyticDSL
    fun screenViewEvent(
            @StringRes name: Int,
            vararg formatArgs: Any,
            builder: ParametersBuilder.() -> Unit = {},
    ) = screenViewEvent {
        screenName(name, formatArgs)
        builder()
    }

    @AnalyticDSL
    fun onClickEvent(
            @StringRes name: Int,
            vararg formatArgs: Any,
            builder: ParametersBuilder.() -> Unit = {},
    ) = onClickEvent {
        itemName(name, formatArgs)
        builder.invoke(this)
    }

    @AnalyticDSL
    fun onClickEvent(name: String, builder: ParametersBuilder.() -> Unit = {},) = onClickEvent {
        itemName(name)
        builder.invoke(this)
    }

    @AnalyticDSL
    fun onClickEvent(
            builder: ParametersBuilder.() -> Unit,
    ) = event(R.string.analytics_on_click) {
        builder()
    }
}

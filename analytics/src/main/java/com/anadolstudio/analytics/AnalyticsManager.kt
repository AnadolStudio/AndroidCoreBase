package com.anadolstudio.analytics

import android.os.Bundle

interface AnalyticsManager {

    fun logEvent(eventName: String, eventBundle: Bundle? = null)

}

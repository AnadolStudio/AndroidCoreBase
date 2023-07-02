package com.anadolstudio.core.base

import android.content.Context
import android.view.View

interface UiEntity {

    fun provideContext(): Context

    fun provideRootView(): View
}

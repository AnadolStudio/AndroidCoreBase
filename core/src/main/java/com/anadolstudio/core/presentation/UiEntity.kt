package com.anadolstudio.core.presentation

import android.content.Context
import android.view.View

interface UiEntity {

    fun provideContext(): Context

    fun provideRootView(): View
}

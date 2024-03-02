package com.anadolstudio.ui

import android.content.Context
import android.view.View

interface UiEntity {

    fun provideContext(): Context

    fun provideRootView(): View
}

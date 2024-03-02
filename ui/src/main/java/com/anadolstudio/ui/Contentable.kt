package com.anadolstudio.ui

import com.anadolstudio.ui.fragment.state_util.ViewStateDelegate
import com.anadolstudio.ui.viewmodel.BaseController

interface Contentable<ViewState : Any, Controller : BaseController> {

    val viewStateDelegate: ViewStateDelegate

    fun render(state: ViewState)

}

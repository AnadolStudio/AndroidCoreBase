package com.anadolstudio.core.presentation

import com.anadolstudio.core.presentation.fragment.state_util.ViewStateDelegate
import com.anadolstudio.core.viewmodel.BaseController

interface Contentable<ViewState : Any, Controller : BaseController> {

    val viewStateDelegate: ViewStateDelegate

    fun render(state: ViewState, controller: Controller)

}

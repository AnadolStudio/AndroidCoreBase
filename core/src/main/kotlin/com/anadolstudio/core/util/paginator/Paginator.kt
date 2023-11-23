package com.anadolstudio.core.util.paginator

interface Paginator {
    companion object {
        const val DEFAULT_FIRST_PAGE_NUMBER = 1
    }

    fun refresh()
    fun restart()
    fun pullToRefresh()
    fun loadNewPage()
}

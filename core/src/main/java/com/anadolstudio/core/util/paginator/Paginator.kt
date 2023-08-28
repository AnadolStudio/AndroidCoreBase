package com.anadolstudio.core.util.paginator

interface Paginator {
    fun refresh()
    fun restart()
    fun pullToRefresh()
    fun loadNewPage()
}

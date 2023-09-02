package com.anadolstudio.core.util.paginator

import com.anadolstudio.core.util.paginator.PagingDataState.*

interface PagingViewController<E> {
    fun onLoading()
    fun onError(error: Throwable)
    fun onEmptyData()
    fun onPageData(data: List<E>)
    fun onUpdateData(data: List<E>)
    fun onNextPageLoading()
    fun onNextPageError(error: Throwable)
    fun onAllData()
    fun onRefreshError(exception: Throwable)
    fun onRefresh()

    class Delegate<Data>(
            private val getCurrentDataAction: () -> List<Data>,
            private val updateStateAction: (PagingDataState<Data>) -> Unit,
            private val updateData: (List<Data>) -> Unit,
    ) : PagingViewController<Data> {

        private val dataList: List<Data> get() = getCurrentDataAction.invoke()

        override fun onLoading() = updateStateAndData(emptyList(), Loading())
        override fun onError(error: Throwable) = updateStateAndData(emptyList(), Error(error))
        override fun onEmptyData() = updateStateAndData(emptyList(), Empty())
        override fun onAllData() = updateStateAction.invoke(Content.AllData())
        override fun onRefreshError(exception: Throwable) = updateStateAction.invoke(Content.RefreshError())
        override fun onNextPageError(error: Throwable) = updateStateAction.invoke(Content.NextErrorPage(error))
        override fun onNextPageLoading() = updateStateAction.invoke(Content.NextLoadingPage())
        override fun onRefresh() = updateStateAction.invoke(Content.Refresh())
        override fun onPageData(data: List<Data>) = updateStateAndData(dataList + data, Content.PageData(data))
        override fun onUpdateData(data: List<Data>) = updateStateAndData(data, Content.UpdateData(data))

        private fun updateStateAndData(data: List<Data>, state: PagingDataState<Data>) {
            updateStateAction.invoke(state)
            updateData.invoke(data)
        }
    }

}

package com.anadolstudio.utils.states

enum class LoadingContext {
    /**
     * Полная перезагруза, обычно должны отображаться скелетоны, но иногда лоадинг в SwipeRefreshLayout
     */
    INIT_LOADING,

    /**
     * Обновления данных, должен отображаться только лоадинг в SwipeRefreshLayout
     */
    REFRESH,

    /**
     * Загрузка, если произошла ошибка, скелетонов быть не должно
     */
    RETRY
}

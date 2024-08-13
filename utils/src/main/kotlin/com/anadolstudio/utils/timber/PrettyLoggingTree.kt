package com.anadolstudio.utils.timber

import android.content.Context
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import timber.log.Timber

class PrettyLoggingTree(context: Context, tag: String?) : Timber.Tree() {

    init {
        val formatStrategy = PrettyFormatStrategy.newBuilder()
                .tag(tag)
                .showThreadInfo(false)
                .methodCount(2)
                .methodOffset(7)
                .build()

        Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))
    }

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        Logger.log(priority, tag, message, t)
    }
}


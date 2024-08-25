package com.anadolstudio.utils.util.rx

import android.os.CountDownTimer
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

private const val TIMER_PERIOD = 1L
private val DEFAULT_TIME_UNIT = TimeUnit.SECONDS

/**
 * ВАЖНО!
 * Метод createReverseTimer нужно использовать только в main потоке, ибо в background потоке CountDownTimer не работает
 */
fun createReverseTimer(
        initialTimerValue: Long,
        countDownInterval: Long = TIMER_PERIOD,
        timeUnit: TimeUnit = DEFAULT_TIME_UNIT
): Observable<Long> = Observable.create { emitter ->
    createCountDownTimer(
            onTickAction = { timeUnits -> emitter.onNext(timeUnits) },
            onFinishAction = { emitter.onComplete() },
            initialTimerValue = initialTimerValue,
            countDownInterval = countDownInterval,
            timeUnit = timeUnit
    ).start()
}

private fun createCountDownTimer(
        onTickAction: (Long) -> Unit,
        onFinishAction: () -> Unit,
        initialTimerValue: Long,
        countDownInterval: Long,
        timeUnit: TimeUnit = DEFAULT_TIME_UNIT
): CountDownTimer = object : CountDownTimer(
        TimeUnit.MILLISECONDS.convert(initialTimerValue, timeUnit),
        TimeUnit.MILLISECONDS.convert(countDownInterval, timeUnit)
) {
    override fun onTick(millisUntilFinished: Long) {
        onTickAction.invoke(timeUnit.convert(millisUntilFinished, TimeUnit.MILLISECONDS))
    }

    override fun onFinish() {
        onFinishAction.invoke()
        cancel()
    }
}

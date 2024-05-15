package ir.ayantech.pishkhanhelper.helper

import android.os.CountDownTimer
import ir.ayantech.whygoogle.helper.LongCallBack
import ir.ayantech.whygoogle.helper.SimpleCallBack
import ir.ayantech.whygoogle.helper.trying
import java.util.concurrent.TimeUnit

fun startTimer(time: Long, onFinish: SimpleCallBack, onTick: LongCallBack): CountDownTimer {
    val timer = object : CountDownTimer(time, 1000) {
        override fun onFinish() {
            trying {
                onFinish()
                cancel()
            }
        }

        override fun onTick(millisUntilFinished: Long) {
            trying {
                val remainingSeconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)
                onTick(remainingSeconds)
            }
        }
    }
    timer.start()
    return timer
}
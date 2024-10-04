package com.example.testapp;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;

public abstract class CountDownTimerExt {

    private long mStopTimeInFuture;

    private long mMillisInFuture;

    private final long mTotalCountdown;

    private final long mCountdownInterval;

    private long mPauseTimeRemaining;

    private boolean mRunAtStart;


    public CountDownTimerExt(long millisOnTimer, long countDownInterval, boolean runAtStart) {
        mMillisInFuture = millisOnTimer;
        mTotalCountdown = mMillisInFuture;
        mCountdownInterval = countDownInterval;
        mRunAtStart = runAtStart;
    }

    public final void cancel() {
        mHandler.removeMessages(MSG);
    }

    public synchronized final CountDownTimerExt create() {
        if (mMillisInFuture <= 0) {
            onFinish();
        } else {
            mPauseTimeRemaining = mMillisInFuture;
        }

        if (mRunAtStart) {
            resume();
        }

        return this;
    }

    public void pause () {
        if (isRunning()) {
            mPauseTimeRemaining = timeLeft();
            cancel();
        }
    }

    public void resumeForce()
    {
        mRunAtStart = true;
        resume();
    }

    public void resume () {
        if (isPaused() & mRunAtStart) {
            mMillisInFuture = mPauseTimeRemaining;
            mStopTimeInFuture = SystemClock.elapsedRealtime() + mMillisInFuture;
            mHandler.sendMessage(mHandler.obtainMessage(MSG));
            mPauseTimeRemaining = 0;

        }
    }

    public boolean isPaused () {
        return (mPauseTimeRemaining > 0);
    }


    public boolean isRunning() {
        return (! isPaused());
    }


    public long timeLeft() {
        long millisUntilFinished;
        if (isPaused()) {
            millisUntilFinished = mPauseTimeRemaining;
        } else {
            millisUntilFinished = mStopTimeInFuture - SystemClock.elapsedRealtime();
            if (millisUntilFinished < 0) millisUntilFinished = 0;
        }
        return millisUntilFinished;
    }

    public abstract void onTick(long millisUntilFinished);


    public abstract void onFinish();


    private static final int MSG = 1;


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            synchronized (CountDownTimerExt.this) {
                long millisLeft = timeLeft();

                if (millisLeft <= 0) {
                    cancel();
                    onFinish();
                } else if (millisLeft < mCountdownInterval) {
                    // no tick, just delay until done
                    sendMessageDelayed(obtainMessage(MSG), millisLeft);
                } else {
                    long lastTickStart = SystemClock.elapsedRealtime();
                    onTick(millisLeft);

                    // take into account user's onTick taking time to execute
                    long delay = mCountdownInterval - (SystemClock.elapsedRealtime() - lastTickStart);

                    // special case: user's onTick took more than mCountdownInterval to
                    // complete, skip to next interval
                    while (delay < 0) delay += mCountdownInterval;

                    sendMessageDelayed(obtainMessage(MSG), delay);
                }
            }
        }
    };
}

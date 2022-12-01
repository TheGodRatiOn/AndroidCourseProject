package com.example.androidcourseproject.dataClasses

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ProgressBar
import android.widget.TextView
import java.lang.Thread.sleep
import java.util.concurrent.atomic.AtomicLong

class ThreadRunner(
    private val threadTextView: TextView,
    private val threadProgressBar: ProgressBar,
    private var isRunning: Boolean,
    private var numCounter: Int,
    private var threadDelay: AtomicLong,
    private val threadHandler: Handler = Handler(Looper.getMainLooper())
) : Runnable {
    override fun run() {
        try {
            isRunning = true
            while (true) {

                checkToContinueLoop()
                sleep(threadDelay.get())
                numCounter++

                threadHandler.post {
                    threadTextView.text = numCounter.toString()
                    if (numCounter % 10 == 0) {
                        threadProgressBar.incrementProgressBy(1)
                    }
                }
            }
        } catch (e: InterruptedException) {
            Log.d("TR", "Thread with id ${android.os.Process.myTid()} got interrupted")
        }
        Log.d("TR", "Thread with id ${android.os.Process.myTid()} is finished")
    }

    fun setThreadDelay(delay: Long) {
        threadDelay.set(delay)
    }

    fun isRunning(): Boolean {
        return isRunning
    }

    @Synchronized
    private fun checkToContinueLoop() {
        while (!isRunning) {
            (this as Object).wait()
        }
    }

    fun suspendThread() {
        isRunning = false
    }

    @Synchronized
    fun resetThread() {
        numCounter = 0
        threadHandler.post {
            threadTextView.text = numCounter.toString()
            threadProgressBar.progress = 0
        }
    }

    @Synchronized
    fun resumeThread() {
        isRunning = true
        (this as Object).notify()
    }
}
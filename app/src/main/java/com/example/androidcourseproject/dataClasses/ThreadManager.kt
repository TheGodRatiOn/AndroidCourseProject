package com.example.androidcourseproject.dataClasses

import android.util.Log
import android.widget.ProgressBar
import android.widget.TextView
import java.util.concurrent.atomic.AtomicLong
import kotlin.concurrent.thread

class ThreadManager(
    firstThreadTextView: TextView,
    secondThreadTextView: TextView,
    firstThreadProgressBar: ProgressBar,
    secondThreadProgressBar: ProgressBar
) {
    private var firstThreadRunner: ThreadRunner =
        getThreadRunner(firstThreadTextView, firstThreadProgressBar, 400)
    private var secondThreadRunner: ThreadRunner =
        getThreadRunner(secondThreadTextView, secondThreadProgressBar, 600)
    private var started: Boolean = false

    fun setFirstThreadDelay(long: Long) {
        firstThreadRunner.setThreadDelay(long)
    }

    fun setSecondThreadDelay(long: Long) {
        secondThreadRunner.setThreadDelay(long)
    }

    fun startOrResume() {
        if (started) {
            resumeCounting()
        } else {
            startCounting()
        }
    }

    private fun startCounting() {
        thread {
            firstThreadRunner.run()
        }
        thread {
            secondThreadRunner.run()
        }
        Log.d("TM", "Checkpoint 2")
        this.started = true
    }

    fun pauseCounting() {
        if (checkIsRunning() and started) {
            firstThreadRunner.suspendThread()
            secondThreadRunner.suspendThread()
        }
    }

    private fun resumeCounting() {
        thread {
            firstThreadRunner.resumeThread()
        }
        thread {
            secondThreadRunner.resumeThread()
        }
    }


    fun resetCounting() {
        resetThreads()
    }

    private fun resetThreads() {
        firstThreadRunner.resetThread()
        secondThreadRunner.resetThread()
    }

    private fun checkIsRunning(): Boolean {
        return firstThreadRunner.isRunning() and secondThreadRunner.isRunning()
    }

    private fun getThreadRunner(
        textView: TextView,
        progressBar: ProgressBar,
        delay: Long
    ): ThreadRunner {
        return ThreadRunner(
            threadTextView = textView,
            threadProgressBar = progressBar,
            isRunning = false,
            numCounter = 0,
            threadDelay = AtomicLong(delay)
        )
    }

    fun isStarted(): Boolean {
        return started
    }

    fun terminateThreads() {
        firstThreadRunner.stopExecution()
        secondThreadRunner.stopExecution()
    }
}
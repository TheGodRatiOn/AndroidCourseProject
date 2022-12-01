package com.example.androidcourseproject.tasks

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.androidcourseproject.R
import com.example.androidcourseproject.dataClasses.ThreadManager
import java.lang.Long

class SecondTaskActivity : AppCompatActivity() {
    private lateinit var activityThreadManager: ThreadManager

    private lateinit var firstThreadTextView: TextView
    private lateinit var secondThreadTextView: TextView

    private lateinit var firstThreadEditText: EditText
    private lateinit var secondThreadEditText: EditText

    private lateinit var firstThreadProgressBar: ProgressBar
    private lateinit var secondThreadProgressBar: ProgressBar

    private lateinit var startCountingButton: Button
    private lateinit var stopCountingButton: Button
    private lateinit var resetCountingButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second_task_activity)
        initScreenElements()
        setTextEditListeners()
        setButtonListeners()

        activityThreadManager = ThreadManager(
            firstThreadTextView,
            secondThreadTextView,
            firstThreadProgressBar,
            secondThreadProgressBar
        )
    }

    private fun initScreenElements() {
        firstThreadTextView = findViewById(R.id.threadOneCounter)
        secondThreadTextView = findViewById(R.id.threadTwoCounter)
        firstThreadEditText = findViewById(R.id.firstThreadDelay)
        secondThreadEditText = findViewById(R.id.secondThreadDelay)
        startCountingButton = findViewById(R.id.startButton)
        stopCountingButton = findViewById(R.id.stopButton)
        resetCountingButton = findViewById(R.id.resetButton)
        firstThreadProgressBar = findViewById(R.id.firstThreadProgressbar)
        secondThreadProgressBar = findViewById(R.id.secondThreadProgressbar)
    }

    private fun setTextEditListeners() {
        firstThreadEditText.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(view: View?, keyCode: Int, event: KeyEvent): Boolean {
                if (event.action == KeyEvent.ACTION_DOWN &&
                    keyCode == KeyEvent.KEYCODE_ENTER
                ) {
                    activityThreadManager.setFirstThreadDelay(Long.valueOf(firstThreadEditText.text.toString()))
                    firstThreadEditText.clearFocus()
                    firstThreadEditText.isCursorVisible = false
                    hideSoftKeyboard(view)
                    return true
                }
                return false
            }
        })

        secondThreadEditText.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(view: View?, keyCode: Int, event: KeyEvent): Boolean {
                if (event.action == KeyEvent.ACTION_DOWN &&
                    keyCode == KeyEvent.KEYCODE_ENTER
                ) {
                    activityThreadManager.setSecondThreadDelay(Long.valueOf(secondThreadEditText.text.toString()))
                    secondThreadEditText.clearFocus()
                    secondThreadEditText.isCursorVisible = false
                    hideSoftKeyboard(view)
                    return true
                }
                return false
            }
        })
    }

    private fun hideSoftKeyboard(view: View?) {
        val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    private fun setButtonListeners() {
        startCountingButton.setOnClickListener {
            activityThreadManager.startOrResume()
        }

        stopCountingButton.setOnClickListener {
            activityThreadManager.pauseCounting()
        }

        resetCountingButton.setOnClickListener {
            activityThreadManager.resetCounting()
        }
    }

    override fun onPause() {
        activityThreadManager.pauseCounting()
        super.onPause()
    }

    override fun onResume() {
        if (activityThreadManager.isStarted()) {
            activityThreadManager.startOrResume()
        }
        super.onResume()
    }

    override fun onDestroy() {
        activityThreadManager.terminateThreads()
        super.onDestroy()
    }
}
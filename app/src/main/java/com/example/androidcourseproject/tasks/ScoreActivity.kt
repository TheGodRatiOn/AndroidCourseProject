package com.example.androidcourseproject.tasks

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.androidcourseproject.R
import com.example.androidcourseproject.utilClasses.ActivitySharedPreferences


class ScoreActivity : AppCompatActivity() {
    private val counterName: String = "scoreCounter"
    private var counterView: TextView? = null
    private lateinit var buttonIncrement: Button
    private lateinit var buttonDecrement: Button
    private lateinit var shPrefs: ActivitySharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.score_activity)
        this.shPrefs = ActivitySharedPreferences(this)
        counterView = findViewById(R.id.tv_pl_counter)
        buttonIncrement = findViewById(R.id.b_inc_counter)
        buttonDecrement = findViewById(R.id.b_dec_counter)
        counterView?.text = this.shPrefs.getIntByKey(counterName).toString()
        buttonIncrement.setOnClickListener(listener)
        buttonDecrement.setOnClickListener(listener)
    }

    private val listener = View.OnClickListener { v: View? ->
        when (v?.id) {
            R.id.b_inc_counter -> {
                incrementCounter()
                updateScreen()
            }
            R.id.b_dec_counter -> {
                decrementCounter()
                updateScreen()
            }
        }
    }

    private fun incrementCounter() {
        val buffer = this.shPrefs.getIntByKey(this.counterName)
        this.shPrefs.saveIntByKey(this.counterName, buffer + 1)
    }

    private fun decrementCounter() {
        val buffer = this.shPrefs.getIntByKey(this.counterName)
        this.shPrefs.saveIntByKey(this.counterName, buffer - 1)
    }

    private fun updateScreen() {
        counterView?.text = this.shPrefs.getIntByKey(this.counterName).toString()
    }
}
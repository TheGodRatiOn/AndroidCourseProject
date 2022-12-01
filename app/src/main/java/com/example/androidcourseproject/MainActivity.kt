package com.example.androidcourseproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.androidcourseproject.tasks.FirstTaskActivity
import com.example.androidcourseproject.tasks.ScoreActivity
import com.example.androidcourseproject.tasks.SecondTaskActivity
import com.example.androidcourseproject.tasks.ThirdTaskActivity

class MainActivity : AppCompatActivity() {
    private lateinit var buttonTaskOne: Button
    private lateinit var buttonTaskTwo: Button
    private lateinit var buttonTaskThree: Button
    private lateinit var buttonScore : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle(R.string.main_activity_bar_name)
        setActivityButtons()
    }

    private fun setActivityButtons() {
        initializeButtons()
        initializeButtonColors()
        initializeButtonListeners()
    }

    private fun initializeButtons() {
        buttonTaskOne = findViewById(R.id.taskButton1)
        buttonTaskTwo = findViewById(R.id.taskButton2)
        buttonTaskThree = findViewById(R.id.taskButton3)
        buttonScore = findViewById(R.id.scoreButton)
    }

    private fun initializeButtonColors() {
        buttonTaskOne.setBackgroundColor(resources.getColor(R.color.corvette_yellow))
        buttonTaskOne.setTextColor(resources.getColor(R.color.black))
        buttonTaskTwo.setBackgroundColor(resources.getColor(R.color.british_racing_green))
        buttonTaskTwo.setTextColor(resources.getColor(R.color.white))
        buttonTaskThree.setBackgroundColor(resources.getColor(R.color.ferrari_red))
        buttonTaskThree.setTextColor(resources.getColor(R.color.black))
        buttonScore.setBackgroundColor(resources.getColor(R.color.black))
    }

    private fun initializeButtonListeners() {
        buttonTaskOne.setOnClickListener {
            val intent = Intent(this, FirstTaskActivity::class.java)
            startActivity(intent)
        }

        buttonTaskTwo.setOnClickListener {
            val intent = Intent(this, SecondTaskActivity::class.java)
            startActivity(intent)
        }

        buttonTaskThree.setOnClickListener {
            val intent = Intent(this, ThirdTaskActivity::class.java)
            startActivity(intent)
        }

        buttonScore.setOnClickListener{
            val intent = Intent(this, ScoreActivity::class.java)
            startActivity(intent)
        }
    }

}
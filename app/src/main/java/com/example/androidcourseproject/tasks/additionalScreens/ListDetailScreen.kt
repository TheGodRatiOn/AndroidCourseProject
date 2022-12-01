package com.example.androidcourseproject.tasks.additionalScreens

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.androidcourseproject.R
import com.example.androidcourseproject.dataClasses.SequenceStorage
import com.example.androidcourseproject.tasks.FirstTaskActivity

class ListDetailScreen : AppCompatActivity() {
    private val sequenceObjectName: String = "sequenceStorageObject"

    private lateinit var sequenceItem: SequenceStorage

    private lateinit var natButton: Button
    private lateinit var fibButton: Button
    private lateinit var colButton: Button

    private lateinit var screenTitle: TextView
    private lateinit var longTextView: TextView

    private lateinit var natTextView: TextView
    private lateinit var fibTextView: TextView
    private lateinit var colTextView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_item_detail_screen)
        initSequenceObject()
        setTextViews()
        setButtons()
    }

    private fun initSequenceObject() {
        val sequence = intent.getParcelableExtra<SequenceStorage>(
            resources.getString(R.string.sequenceStorageIntentName)
        )

        if (sequence != null) {
            this.sequenceItem = sequence
        } else {
            Log.e("LDS.SS_INIT", "Received null object")
        }
    }

    private fun setTextViews() {
        initTextViews()
        setTextForTextView()
    }

    private fun initTextViews(){
        natTextView = findViewById(R.id.textViewNat)
        fibTextView = findViewById(R.id.textViewFib)
        colTextView = findViewById(R.id.textViewCol)
        screenTitle = findViewById(R.id.screenTitleTextView)
        longTextView = findViewById(R.id.longTextView)
    }

    private fun setTextForTextView() {
        natTextView.text = sequenceItem.getNatural()
        fibTextView.text = sequenceItem.getFibonacci()
        colTextView.text = sequenceItem.getCollatz()
        screenTitle.setText(R.string.screenTitle)
        longTextView.setText(R.string.long_text_resource)
    }

    private fun setButtons() {
        initButtons()
        setButtonListener()
        setColors()
    }

    private fun initButtons(){
        natButton = findViewById(R.id.buttonNat)
        fibButton = findViewById(R.id.buttonFib)
        colButton = findViewById(R.id.buttonCol)
    }

    private fun setButtonListener(){
        natButton.setOnClickListener {
            sequenceItem.nextNatural()
            natTextView.text = sequenceItem.getNatural()
        }

        fibButton.setOnClickListener {
            sequenceItem.nextFibonacci()
            fibTextView.text = sequenceItem.getFibonacci()
        }

        colButton.setOnClickListener {
            sequenceItem.nextCollatz()
            colTextView.text = sequenceItem.getCollatz()
        }
    }

    private fun setColors(){
        natButton.setBackgroundColor(resources.getColor(R.color.ferrari_red))
        fibButton.setBackgroundColor(resources.getColor(R.color.ferrari_red))
        colButton.setBackgroundColor(resources.getColor(R.color.ferrari_red))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(sequenceObjectName, this.sequenceItem)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val some = savedInstanceState.get(sequenceObjectName)
        if (some != null && some is SequenceStorage){
            this.sequenceItem = some
        } else {
            Log.e("LDS", "Null object passed")
        }
    }

    override fun onBackPressed() {
        val data = Intent()
        data.putExtra(resources.getString(R.string.listItemScreenResultName), sequenceItem)
        setResult(Activity.RESULT_OK, data)
        super.onBackPressed()
    }
}
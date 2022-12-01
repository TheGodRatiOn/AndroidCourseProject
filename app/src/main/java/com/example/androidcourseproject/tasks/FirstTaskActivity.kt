package com.example.androidcourseproject.tasks

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.isVisible
import com.example.androidcourseproject.MainActivity
import com.example.androidcourseproject.R
import com.example.androidcourseproject.dataClasses.SequenceStorage
import com.example.androidcourseproject.tasks.additionalScreens.ListDetailScreen
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FirstTaskActivity : AppCompatActivity() {
    private val sequencesArrayName: String = "SEQUENCE_ELEMENTS_ARRAY"
    private val switchStateName: String = "SWITCH_STATE"
    private val numberOfElements : Int = 5
    private lateinit var editTextView: EditText
    private lateinit var labelTextView: TextView
    private lateinit var toastButton: Button
    private lateinit var hideListButton: Button
    private lateinit var fabButton: FloatingActionButton
    private lateinit var listView: ListView
    private lateinit var toggleButton: SwitchCompat
    private lateinit var listAdapter: ArrayAdapter<SequenceStorage>
    private lateinit var launcher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.first_task_activity)

        launcher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                onActivityResult(result)
            }

        setTitle(R.string.first_task_activity_one_name)
        setTextViews()
        setButtons()
        setDefaultListView()
        setToggleButtons()
    }

    private fun setDefaultListView() {
        setListAdapter()
        setListViewListener()
    }

    private fun setListAdapter() {
        listView = findViewById(R.id.list_view_for_sequences)
        listAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            getListViewItems())
        listView.adapter = listAdapter
    }

    private fun setListViewListener() {
        listView.setOnItemClickListener { adapterView, _, item, _ ->
            val intent = Intent(this, ListDetailScreen::class.java)
            val selected: Any = adapterView.getItemAtPosition(item)
            if (selected is SequenceStorage) {
                intent.putExtra(resources.getString(R.string.sequenceStorageIntentName), selected)
                launcher.launch(intent)
            } else {
                Log.e("FTA.LV", "Wrong list type")
            }
        }
    }

    private fun onActivityResult(result: ActivityResult) {
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.let {
                val sequence = it.getParcelableExtra<SequenceStorage>(
                    resources.getString(R.string.listItemScreenResultName)
                )
                if (sequence != null) {
                    updateAdapterList(sequence)
                    listAdapter.notifyDataSetChanged()
                } else {
                    Log.e("FTA", "Null received from list detail activity")
                }
            }
        }
    }

    private fun updateAdapterList(toInsert : SequenceStorage){
        val toDelete =  listAdapter.getItem(toInsert.getId())
        listAdapter.remove(toDelete)
        listAdapter.insert(toInsert, toInsert.getId())
    }

    private fun getListViewItems() = Array(numberOfElements)
    { SequenceStorage(it) }.toMutableList()


    private fun setTextViews() {
        editTextView = findViewById(R.id.edit_text_for_label)
        labelTextView = findViewById(R.id.text_view_label)
        setUpTextListeners()
    }

    private fun setButtons() {
        toastButton = findViewById(R.id.show_toast)
        hideListButton = findViewById(R.id.hide_list_button)
        fabButton = findViewById(R.id.floatingActionButton)

        setUpButtonColors()
        setUpButtonListeners()
    }

    private fun setToggleButtons() {
        toggleButton = findViewById(R.id.toggle_button)
        setUpToggleButtonListeners()
    }

    private fun setUpToggleButtonListeners() {
        toggleButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                labelTextView.setTextColor(resources.getColor(R.color.ferrari_red))
            } else {
                labelTextView.setTextColor(resources.getColor(R.color.black))
            }
            println("Toggle is checked: $isChecked")
        }
    }

    private fun setUpTextListeners() {
        editTextView.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(view: View?, keyCode: Int, event: KeyEvent): Boolean {
                if (event.action == KeyEvent.ACTION_DOWN &&
                    keyCode == KeyEvent.KEYCODE_ENTER
                ) {
                    labelTextView.text = editTextView.text
                    editTextView.clearFocus()
                    editTextView.isCursorVisible = false
                    hideSoftKeyboard(view)
                    return true
                }
                return false
            }
        })
    }

    private fun setUpButtonColors() {
        toastButton.setBackgroundColor(resources.getColor(R.color.black))
        hideListButton.setBackgroundColor(resources.getColor(R.color.corvette_yellow))
        hideListButton.setTextColor(resources.getColor(R.color.black))
    }

    private fun setUpButtonListeners() {
        toastButton.setOnClickListener {
            Toast.makeText(
                this,
                R.string.toast_text,
                Toast.LENGTH_SHORT
            ).show()
        }

        hideListButton.setOnClickListener {
            if (listView.isVisible) {
                listView.visibility = View.INVISIBLE
            } else {
                listView.visibility = View.VISIBLE
            }
        }

        fabButton.setOnClickListener {
            val newIntent = Intent(this, MainActivity::class.java)
            startActivity(newIntent)
        }
    }

    private fun hideSoftKeyboard(view: View?) {
        val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        val range = 0 until listAdapter.count

        val buffer = Array(listAdapter.count) { i -> SequenceStorage(i) }
        for (i in range) {
            listAdapter.getItem(i)?.let { buffer[i] = it }
        }

        outState.putParcelableArray(sequencesArrayName, buffer)

        if (toggleButton.isChecked) {
            outState.putInt(switchStateName, 1)
        } else {
            outState.putInt(switchStateName, 0)
        }
        super.onSaveInstanceState(outState)
    }

    private fun restoreViewValues(savedInstanceState: Bundle) {
        val viewElements = savedInstanceState.getParcelableArray(sequencesArrayName)
        val sequenceElements: Array<SequenceStorage> = Array(5) { id -> SequenceStorage(id) }
        val range = 0..((viewElements?.size?.minus(1)) ?: 0)

        if (viewElements != null) {
            for (i in range) {
                sequenceElements[i] = viewElements[i] as SequenceStorage
            }
        }

        this.listAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            sequenceElements
        )
        listView.adapter = listAdapter
    }

    private fun restoreToggleButtonState(savedInstanceState: Bundle) {
        toggleButton = findViewById(R.id.toggle_button)
        toggleButton.isChecked = savedInstanceState.get(switchStateName) == 1
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.v("onRestoreInstance", "Restoring Task 1 instance")
        restoreViewValues(savedInstanceState)
        restoreToggleButtonState(savedInstanceState)
    }

}

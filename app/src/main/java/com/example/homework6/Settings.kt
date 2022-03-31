package com.example.homework6

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.homework6.databinding.ActivitySettingsBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Settings : AppCompatActivity() {

    private var mode : String? = "Length"
    private var fromVal : String? = "Yards"
    private var toVal : String? = "Meters"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setSupportActionBar(findViewById(R.id.toolbar))

        if (intent.hasExtra("mode")) {
            mode = intent.getStringExtra("mode")
        }

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            val intent = Intent()
            intent.putExtra("fromVal", fromVal)
            intent.putExtra("toVal", toVal)
            setResult(RESULT_OK, intent)
            finish()
        }

        val fromSpinner = findViewById<Spinner>(R.id.fromSpinner)
        val toSpinner = findViewById<Spinner>(R.id.toSpinner)
        if (mode == "Length") {
            val adapter = ArrayAdapter.createFromResource(
                this, R.array.Length,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item
            )
            adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item)
            fromSpinner.adapter = adapter
            toSpinner.adapter = adapter

            val fromPos = adapter.getPosition(fromVal)
            fromSpinner.setSelection(fromPos)

            val toPos = adapter.getPosition(toVal)
            toSpinner.setSelection(toPos)
        }
        else if (mode == "Volume") {
            val adapter = ArrayAdapter.createFromResource(
                this, R.array.Volume,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item
            )
            adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item)
            fromSpinner.adapter = adapter
            toSpinner.adapter = adapter

            val fromPos = adapter.getPosition(fromVal)
            fromSpinner.setSelection(fromPos)

            val toPos = adapter.getPosition(toVal)
            toSpinner.setSelection(toPos)
        }

        fromSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View?, i: Int, l: Long) {
                fromVal = adapterView.getItemAtPosition(i) as String
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }

        toSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View?, i: Int, l: Long) {
                toVal = adapterView.getItemAtPosition(i) as String
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }
    }
}
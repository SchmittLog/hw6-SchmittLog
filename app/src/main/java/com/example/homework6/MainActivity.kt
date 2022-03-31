package com.example.homework6

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    var unitMode = "Length"
    var fromSettings = "Yards"
    var toSettings = "Meters"
    var toLabel: TextView? = null
    var fromLabel: TextView? = null


    private fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = this.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_settings) {
            val intent = Intent(this@MainActivity, Settings::class.java)
            intent.putExtra("mode", unitMode)
            settingsLauncher.launch(intent)
            return true;
        }
        return false;
    }

    var settingsLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                fromSettings = data?.getStringExtra("fromVal") ?: "Yards"
                toSettings = data?.getStringExtra("toVal") ?: "Meters"
                fromLabel?.text = fromSettings
                toLabel?.text = toSettings
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val titleLabel = findViewById<TextView>(R.id.converterTitle)
        val fromInput = findViewById<EditText>(R.id.fromInput)
        val toInput = findViewById<EditText>(R.id.toInput)
        val calcButton = findViewById<Button>(R.id.calcButton)
        val clearButton = findViewById<Button>(R.id.clearButton)
        val modeButton = findViewById<Button>(R.id.modeButton)
        var fromLength = UnitsConverter.LengthUnits.Yards
        var toLength = UnitsConverter.LengthUnits.Meters
        var fromVolume = UnitsConverter.VolumeUnits.Gallons
        var toVolume = UnitsConverter.VolumeUnits.Liters
        toLabel = findViewById<TextView>(R.id.toLabel)
        fromLabel = findViewById<TextView>(R.id.fromLabel)

        fromInput.setOnFocusChangeListener { view, b ->
            toInput.setText("")
            fromInput.setText("")
        }

        fromInput.setOnClickListener { v->
            fromInput.setText("")
            toInput.setText("")
        }

        toInput.setOnClickListener { v->
            fromInput.setText("")
            toInput.setText("")
        }

       /* if (fromLabel.text.toString == "Yards") {
           fromConvert = UnitsConverter.LengthUnits.Yards
       } */

        calcButton.setOnClickListener { v->
            val fromValue = fromInput.text.toString()
            val toValue = toInput.text.toString()

            if (fromValue != "" && unitMode == "Length") {
                val fromNum = fromValue.toDouble()

                when (fromLabel?.text.toString()) {
                    "Yards" -> fromLength = UnitsConverter.LengthUnits.Yards
                    "Meters" -> fromLength = UnitsConverter.LengthUnits.Meters
                    "Miles" -> fromLength = UnitsConverter.LengthUnits.Miles
                }

                when (toLabel?.text.toString()) {
                    "Yards" -> toLength = UnitsConverter.LengthUnits.Yards
                    "Meters" -> toLength = UnitsConverter.LengthUnits.Meters
                    "Miles" -> toLength = UnitsConverter.LengthUnits.Miles
                }
                toInput.setText(UnitsConverter.convert(fromNum, fromLength, toLength).toString())
            }
            else if (toValue != "" && unitMode == "Length") {
                val toNum = toValue.toDouble()

                when (fromLabel?.text.toString()) {
                    "Yards" -> fromLength = UnitsConverter.LengthUnits.Yards
                    "Meters" -> fromLength = UnitsConverter.LengthUnits.Meters
                    "Miles" -> fromLength = UnitsConverter.LengthUnits.Miles
                }

                when (toLabel?.text.toString()) {
                    "Yards" -> toLength = UnitsConverter.LengthUnits.Yards
                    "Meters" -> toLength = UnitsConverter.LengthUnits.Meters
                    "Miles" -> toLength = UnitsConverter.LengthUnits.Miles
                }
                fromInput.setText(UnitsConverter.convert(toNum, toLength, fromLength).toString())
            } else if (fromValue != "" && unitMode == "Volume") {
                val fromNum = fromValue.toDouble()

                when (fromLabel?.text.toString()) {
                    "Gallons" -> fromVolume = UnitsConverter.VolumeUnits.Gallons
                    "Liters" -> fromVolume = UnitsConverter.VolumeUnits.Liters
                    "Quarts" -> fromVolume = UnitsConverter.VolumeUnits.Quarts
                }

                when (toLabel?.text.toString()) {
                    "Gallons" -> toVolume = UnitsConverter.VolumeUnits.Gallons
                    "Liters" -> toVolume = UnitsConverter.VolumeUnits.Liters
                    "Quarts" -> toVolume = UnitsConverter.VolumeUnits.Quarts
                }
                toInput.setText(UnitsConverter.convert(fromNum, fromVolume, toVolume).toString())
            }
            else if (toValue != "" && unitMode == "Volume") {
                val toNum = toValue.toDouble()

                when (fromLabel?.text.toString()) {
                    "Gallons" -> fromVolume = UnitsConverter.VolumeUnits.Gallons
                    "Liters" -> fromVolume = UnitsConverter.VolumeUnits.Liters
                    "Quarts" -> fromVolume = UnitsConverter.VolumeUnits.Quarts
                }

                when (toLabel?.text.toString()) {
                    "Gallons" -> toVolume = UnitsConverter.VolumeUnits.Gallons
                    "Liters" -> toVolume = UnitsConverter.VolumeUnits.Liters
                    "Quarts" -> toVolume = UnitsConverter.VolumeUnits.Quarts
                }
                fromInput.setText(UnitsConverter.convert(toNum, toVolume, fromVolume).toString())
            }
            hideKeyboard()
        }

            clearButton.setOnClickListener { v->
                fromInput.setText("")
                toInput.setText("")
                hideKeyboard()
            }

            modeButton.setOnClickListener { v->
                if (unitMode == "Length") {
                    titleLabel.text = "Volume Converter"
                    fromLabel?.text = "Gallons"
                    toLabel?.text = "Liters"
                    unitMode = "Volume"
                } else {
                    titleLabel.text = "Length Converter"
                    fromLabel?.text = "Yards"
                    toLabel?.text = "Meters"
                    unitMode = "Length"
                }
                fromInput.setText("")
                toInput.setText("")
                hideKeyboard()
            }
        }
    }


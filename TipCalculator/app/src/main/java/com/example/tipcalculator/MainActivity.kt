package com.example.tipcalculator

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.tipcalculator.databinding.ActivityMainBinding
import java.text.NumberFormat

/**
 * Activity that displays a tip calculator.
 */
class MainActivity : AppCompatActivity() {

    // Binding object instance with access to the views in the activity_main.xml layout
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout XML file and return a binding object instance
        binding = ActivityMainBinding.inflate(layoutInflater)

        // Set the content view of the Activity to be the root view of the layout
        setContentView(binding.root)

        // Initialise tipResult with an empty string
        binding.tipResult.text = ""

        // Setup a key listener on the EditText to hide the keypad when the user presses the Enter key
        binding.costOfServiceEditText.setOnKeyListener { view, keyCode, _ -> handleKeyEvent(view, keyCode) }

        // Setup a click listener on the calculate button to calculate the tip
        binding.calculateButton.setOnClickListener { calculateTip() }
    }

    /**
     * Calculates the tip based on the user input.
     */
    private fun calculateTip() {
        // Get the decimal value from the cost of service EditText field
        val stringInTextField = binding.costOfServiceEditText.text.toString()
        val cost = stringInTextField.toDoubleOrNull()

        // If the cost is null or 0, then display no tip amount and exit this function early.
        if (cost == null) {
            binding.tipResult.text = ""
            return
        }

        // Get the tip percentage based on which radio button is selected
        val tipPercentage = when(binding.tipOptions.checkedRadioButtonId) {
            R.id.option_twenty_percent -> 0.20
            R.id.option_eighteen_percent -> 0.18
            else -> 0.10
        }

        // Calculate the tip
        var tip = tipPercentage * cost

        // If the switch for rounding up the tip toggled on (isChecked is true), then round up the
        // tip. Otherwise do not change the tip value.
        if(binding.roundUpSwitch.isChecked) {
            // Take the ceiling of the current tip, which rounds up to the next integer, and store
            // the new value in the tip variable.
            tip = kotlin.math.ceil(tip)
        }

        // Display the formatted tip value onscreen
        displayTip(tip)
    }

    /**
     * Format the tip amount according to the local currency and display it onscreen.
     * Example would be "Tip Amount: $10.00".
     */
    private fun displayTip(tip: Double) {
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
    }

    /**
     * Key listener for hiding the keyboard when the "Enter" button is tapped.
     */
    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}
package com.funny.dartmade

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MatchSettingsActivity : AppCompatActivity() {

    private lateinit var editPlayerOne: EditText
    private lateinit var editPlayerTwo: EditText
    private lateinit var checkBoxAI: CheckBox
    private lateinit var spinnerAILevel: Spinner
    private lateinit var spinnerSets: Spinner
    private lateinit var buttonStartMatch: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_settings)

        editPlayerOne = findViewById(R.id.editPlayerOne)
        editPlayerTwo = findViewById(R.id.editPlayerTwo)
        checkBoxAI = findViewById(R.id.checkBoxAI)
        spinnerAILevel = findViewById(R.id.spinnerAILevel)
        spinnerSets = findViewById(R.id.spinnerSets)
        buttonStartMatch = findViewById(R.id.buttonStartMatch)

        checkBoxAI.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                editPlayerTwo.isEnabled = false
                editPlayerTwo.setText("AI Opponent")
                spinnerAILevel.visibility = View.VISIBLE
            } else {
                editPlayerTwo.isEnabled = true
                editPlayerTwo.setText("")
                spinnerAILevel.visibility = View.GONE
            }
        }

        buttonStartMatch.setOnClickListener {
            startMatch()
        }
    }

    private fun startMatch() {
        val playerOneName = editPlayerOne.text.toString()
        val playerTwoName = editPlayerTwo.text.toString()
        val isAI = checkBoxAI.isChecked
        val aiLevel = if (isAI) spinnerAILevel.selectedItem.toString() else ""
        val bestOfSets = spinnerSets.selectedItem.toString().toInt()

        if (playerOneName.isEmpty()) {
            showToast("Please enter Player 1 name")
            return
        }
        if (!isAI && playerTwoName.isEmpty()) {
            showToast("Please enter Player 2 name")
            return
        }

        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("PLAYER_ONE_NAME", playerOneName)
            putExtra("PLAYER_TWO_NAME", playerTwoName)
            putExtra("IS_AI", isAI)
            putExtra("AI_LEVEL", aiLevel)
            putExtra("BEST_OF_SETS", bestOfSets)
        }
        startActivity(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

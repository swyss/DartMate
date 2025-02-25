package com.funny.dartmade

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private var playerOneScore = 501
    private var playerTwoScore = 501
    private var playerOneLegs = 0
    private var playerTwoLegs = 0
    private var playerOneSets = 0
    private var playerTwoSets = 0
    private var totalDartsOne = 0
    private var totalDartsTwo = 0
    private var isPlayerOneTurn = true
    private var dartsThrown = 0
    private var roundScore = 0

    private lateinit var txtPointPlayerONE: TextView
    private lateinit var txtPointPlayerTWO: TextView
    private lateinit var txtLegsPlayerONE: TextView
    private lateinit var txtLegsPlayerTWO: TextView
    private lateinit var txtSetsPlayerONE: TextView
    private lateinit var txtSetsPlayerTWO: TextView
    private lateinit var txtDartsThrown: TextView
    private lateinit var txtRoundScore: TextView
    private lateinit var txtTotalDartsONE: TextView
    private lateinit var txtTotalDartsTWO: TextView
    private lateinit var txtAveragePlayerONE: TextView
    private lateinit var txtAveragePlayerTWO: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialisiere die UI-Elemente
        txtPointPlayerONE = findViewById(R.id.txtPointPlayerONE)
        txtPointPlayerTWO = findViewById(R.id.txtPointPlayerTWO)
        txtLegsPlayerONE = findViewById(R.id.txtLegsPlayerONE)
        txtLegsPlayerTWO = findViewById(R.id.txtLegsPlayerTWO)
        txtSetsPlayerONE = findViewById(R.id.txtSetsPlayerONE)
        txtSetsPlayerTWO = findViewById(R.id.txtSetsPlayerTWO)
        txtDartsThrown = findViewById(R.id.txtDartsThrown)
        txtRoundScore = findViewById(R.id.txtRoundScore)
        txtTotalDartsONE = findViewById(R.id.txtTotalDartsONE)
        txtTotalDartsTWO = findViewById(R.id.txtTotalDartsTWO)
        txtAveragePlayerONE = findViewById(R.id.txtAveragePlayerONE)
        txtAveragePlayerTWO = findViewById(R.id.txtAveragePlayerTWO)

        findViewById<Button>(R.id.buttonReset).setOnClickListener { resetGame() }

        // Dummy-Tasten für Dart-Würfe (Kann für Buttons angepasst werden)
        findViewById<Button>(R.id.button20).setOnClickListener { handleDartThrow(20) }
        findViewById<Button>(R.id.buttonD20).setOnClickListener { handleDartThrow(40) }
        findViewById<Button>(R.id.buttonT20).setOnClickListener { handleDartThrow(60) }
        findViewById<Button>(R.id.button19).setOnClickListener { handleDartThrow(19) }
        findViewById<Button>(R.id.buttonD19).setOnClickListener { handleDartThrow(38) }
        findViewById<Button>(R.id.buttonT19).setOnClickListener { handleDartThrow(57) }
        findViewById<Button>(R.id.button18).setOnClickListener { handleDartThrow(18) }
        findViewById<Button>(R.id.buttonD18).setOnClickListener { handleDartThrow(36) }
        findViewById<Button>(R.id.buttonT18).setOnClickListener { handleDartThrow(54) }
        findViewById<Button>(R.id.button17).setOnClickListener { handleDartThrow(17) }
        findViewById<Button>(R.id.buttonD17).setOnClickListener { handleDartThrow(34) }
        findViewById<Button>(R.id.buttonT17).setOnClickListener { handleDartThrow(51) }
        findViewById<Button>(R.id.button16).setOnClickListener { handleDartThrow(16) }
        findViewById<Button>(R.id.buttonD16).setOnClickListener { handleDartThrow(32) }
        findViewById<Button>(R.id.buttonT16).setOnClickListener { handleDartThrow(48) }
        findViewById<Button>(R.id.button15).setOnClickListener { handleDartThrow(15) }
        findViewById<Button>(R.id.buttonD15).setOnClickListener { handleDartThrow(30) }
        findViewById<Button>(R.id.buttonT15).setOnClickListener { handleDartThrow(45) }
        findViewById<Button>(R.id.button14).setOnClickListener { handleDartThrow(14) }
        findViewById<Button>(R.id.buttonD14).setOnClickListener { handleDartThrow(28) }
        findViewById<Button>(R.id.buttonT14).setOnClickListener { handleDartThrow(42) }
        findViewById<Button>(R.id.button13).setOnClickListener { handleDartThrow(13) }
        findViewById<Button>(R.id.buttonD13).setOnClickListener { handleDartThrow(26) }
        findViewById<Button>(R.id.buttonT13).setOnClickListener { handleDartThrow(39) }

    }

    private fun handleDartThrow(points: Int) {
        lastThrow = points
        roundScore += points
        dartsThrown++

        if (isPlayerOneTurn) totalDartsOne++ else totalDartsTwo++
        updateDartStats()

        if (dartsThrown == 3) {
            applyRoundScore()
            dartsThrown = 0
            roundScore = 0
            isPlayerOneTurn = !isPlayerOneTurn
        }
    }

    private fun applyRoundScore() {
        if (isPlayerOneTurn) {
            playerOneScore -= roundScore
            txtPointPlayerONE.text = playerOneScore.toString()
        } else {
            playerTwoScore -= roundScore
            txtPointPlayerTWO.text = playerTwoScore.toString()
        }

        checkWin()
    }

    private fun checkWin() {
        when {
            playerOneScore <= 0 -> {
                playerOneLegs++
                txtLegsPlayerONE.text = "Legs: $playerOneLegs"
                checkSetWin()
                resetLeg()
            }
            playerTwoScore <= 0 -> {
                playerTwoLegs++
                txtLegsPlayerTWO.text = "Legs: $playerTwoLegs"
                checkSetWin()
                resetLeg()
            }
        }
    }

    private fun checkSetWin() {
        if (playerOneLegs == 2) {
            playerOneSets++
            playerOneLegs = 0
            playerTwoLegs = 0
            txtSetsPlayerONE.text = "Sets: $playerOneSets"
            checkMatchWin()
        } else if (playerTwoLegs == 2) {
            playerTwoSets++
            playerOneLegs = 0
            playerTwoLegs = 0
            txtSetsPlayerTWO.text = "Sets: $playerTwoSets"
            checkMatchWin()
        }
    }

    private fun checkMatchWin() {
        if (playerOneSets == bestOfSets) {
            showToast("Player One Wins the Match!")
            resetGame()
        } else if (playerTwoSets == bestOfSets) {
            showToast("Player Two Wins the Match!")
            resetGame()
        }
    }

    private fun updateDartStats() {
        txtDartsThrown.text = "Darts: $dartsThrown/3"
        txtRoundScore.text = "Round Score: $roundScore"
        txtTotalDartsONE.text = "Total Darts: $totalDartsOne"
        txtTotalDartsTWO.text = "Total Darts: $totalDartsTwo"

        val avgOne = if (totalDartsOne > 0) (501 - playerOneScore) / (totalDartsOne / 3.0) else 0.0
        val avgTwo = if (totalDartsTwo > 0) (501 - playerTwoScore) / (totalDartsTwo / 3.0) else 0.0

        txtAveragePlayerONE.text = "Avg: %.2f".format(avgOne)
        txtAveragePlayerTWO.text = "Avg: %.2f".format(avgTwo)
    }

    private fun undoLastThrow() {
        if (lastThrow > 0) {
            roundScore -= lastThrow
            if (isPlayerOneTurn) {
                playerOneScore += lastThrow
                txtPointPlayerONE.text = playerOneScore.toString()
            } else {
                playerTwoScore += lastThrow
                txtPointPlayerTWO.text = playerTwoScore.toString()
            }
            lastThrow = 0
            showToast("Last throw undone")
        }
    }

    private fun resetLeg() {
        playerOneScore = 501
        playerTwoScore = 501
        dartsThrown = 0
        roundScore = 0
    }

    private fun resetGame() {
        playerOneSets = 0
        playerTwoSets = 0
        resetLeg()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
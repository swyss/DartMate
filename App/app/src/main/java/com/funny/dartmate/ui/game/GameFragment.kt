package com.funny.dartmate.ui.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.funny.dartmate.databinding.FragmentGameBinding

class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!

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
    private var lastThrow = 0
    private val bestOfSets = 3

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val gameViewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        _binding = FragmentGameBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // UI-Elemente initialisieren
        setupUI()
        setupListeners()

        return root
    }

    private fun setupUI() {
        binding.txtPointPlayerONE.text = playerOneScore.toString()
        binding.txtPointPlayerTWO.text = playerTwoScore.toString()
        binding.txtLegsPlayerONE.text = "Legs: $playerOneLegs"
        binding.txtLegsPlayerTWO.text = "Legs: $playerTwoLegs"
        binding.txtSetsPlayerONE.text = "Sets: $playerOneSets"
        binding.txtSetsPlayerTWO.text = "Sets: $playerTwoSets"
    }

    private fun setupListeners() {
        binding.buttonReset.setOnClickListener { resetGame() }

        listOf(
            binding.button20 to 20, binding.buttonD20 to 40, binding.buttonT20 to 60,
            binding.button19 to 19, binding.buttonD19 to 38, binding.buttonT19 to 57,
            binding.button18 to 18, binding.buttonD18 to 36, binding.buttonT18 to 54,
            binding.button17 to 17, binding.buttonD17 to 34, binding.buttonT17 to 51,
            binding.button16 to 16, binding.buttonD16 to 32, binding.buttonT16 to 48
        ).forEach { (button, points) ->
            button.setOnClickListener { handleDartThrow(points) }
        }
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
            binding.txtPointPlayerONE.text = playerOneScore.toString()
        } else {
            playerTwoScore -= roundScore
            binding.txtPointPlayerTWO.text = playerTwoScore.toString()
        }
        checkWin()
    }

    private fun checkWin() {
        when {
            playerOneScore <= 0 -> {
                playerOneLegs++
                binding.txtLegsPlayerONE.text = "Legs: $playerOneLegs"
                checkSetWin()
                resetLeg()
            }
            playerTwoScore <= 0 -> {
                playerTwoLegs++
                binding.txtLegsPlayerTWO.text = "Legs: $playerTwoLegs"
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
            binding.txtSetsPlayerONE.text = "Sets: $playerOneSets"
            checkMatchWin()
        } else if (playerTwoLegs == 2) {
            playerTwoSets++
            playerOneLegs = 0
            playerTwoLegs = 0
            binding.txtSetsPlayerTWO.text = "Sets: $playerTwoSets"
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
        binding.txtDartsThrown.text = "Darts: $dartsThrown/3"
        binding.txtRoundScore.text = "Round Score: $roundScore"
        binding.txtTotalDartsONE.text = "Total Darts: $totalDartsOne"
        binding.txtTotalDartsTWO.text = "Total Darts: $totalDartsTwo"

        val avgOne = if (totalDartsOne > 0) (501 - playerOneScore) / (totalDartsOne / 3.0) else 0.0
        val avgTwo = if (totalDartsTwo > 0) (501 - playerTwoScore) / (totalDartsTwo / 3.0) else 0.0

        binding.txtAveragePlayerONE.text = "Avg: %.2f".format(avgOne)
        binding.txtAveragePlayerTWO.text = "Avg: %.2f".format(avgTwo)
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
        setupUI()
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

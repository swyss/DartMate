package com.funny.dartmate.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.funny.dartmate.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var editPlayerOne: EditText
    private lateinit var editPlayerTwo: EditText
    private lateinit var checkBoxAI: CheckBox
    private lateinit var spinnerAILevel: Spinner
    private lateinit var spinnerSets: Spinner
    private lateinit var buttonStartMatch: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // UI-Elemente initialisieren
        editPlayerOne = binding.editPlayerOne
        editPlayerTwo = binding.editPlayerTwo
        checkBoxAI = binding.checkBoxAI
        spinnerAILevel = binding.spinnerAILevel
        spinnerSets = binding.spinnerSets
        buttonStartMatch = binding.buttonStartMatch

        setupListeners()

        return root
    }

    private fun setupListeners() {
        checkBoxAI.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                editPlayerTwo.isEnabled = false
                editPlayerTwo.setText("AI Opponent")
                spinnerAILevel.visibility = View.VISIBLE
            } else {
                editPlayerTwo.isEnabled = true
                editPlayerTwo.text.clear()
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
            Toast.makeText(requireContext(), "Please enter Player 1 name", Toast.LENGTH_SHORT).show()
            return
        }
        if (!isAI && playerTwoName.isEmpty()) {
            Toast.makeText(requireContext(), "Please enter Player 2 name", Toast.LENGTH_SHORT).show()
            return
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

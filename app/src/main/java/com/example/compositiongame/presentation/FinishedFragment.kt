package com.example.compositiongame.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import com.example.compositiongame.R
import com.example.compositiongame.databinding.FragmentFinishedBinding
import com.example.compositiongame.domain.entity.GameResult


class FinishedFragment : Fragment() {
    private lateinit var binding: FragmentFinishedBinding
    private lateinit var gameResult:GameResult

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            gameResult = it.getParcelable<GameResult>(KEY_GAME_RESULT) as GameResult
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews()
        setupClickListeners()
    }

    private fun setupClickListeners() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    retryGame()
                }
            })
        binding.buttonRetry.setOnClickListener {
            retryGame()
        }
    }

    @SuppressLint("StringFormatMatches")
    private fun bindViews() {
        with(binding) {
            emojiResult.setImageResource(getSmileResId())
            tvRequiredAnswers.text = String.format(
                getString(R.string.required_score),
                gameResult.gameSettings.minCountOfRightAnswers
            )
            tvScoreAnswers.text = String.format(
                getString(R.string.score_answers),
                gameResult.countOfRightAnswers
            )
            tvRequiredPercentage.text = String.format(
                getString(R.string.required_percentage),
                gameResult.gameSettings.minPercentOfRightAnswers
            )
            tvScorePercentage.text = String.format(
                getString(R.string.score_percentage),
                getPercentOfRightAnswers()
            )
        }
    }

    private fun getSmileResId(): Int {
        return if (gameResult.winner) {
            R.drawable.ic_smile
        } else {
            R.drawable.ic_sad
        }
    }

    private fun getPercentOfRightAnswers() = with(gameResult) {
        if (countOfQuestions == 0) {
            0
        } else {
            ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
        }
    }


    private fun retryGame() {
        requireActivity().supportFragmentManager.popBackStack(ChooseLevelFragment.NAME, 0)
    }

    companion object {
        private const val KEY_GAME_RESULT = "game_result"

        @JvmStatic
        fun newInstance(gameResult: GameResult) =
            FinishedFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_GAME_RESULT, gameResult)
                }
            }
    }
}
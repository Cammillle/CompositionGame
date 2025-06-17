package com.example.compositiongame.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.compositiongame.R
import com.example.compositiongame.databinding.FragmentChooseLevelBinding
import com.example.compositiongame.databinding.FragmentGameBinding
import com.example.compositiongame.domain.entity.GameResult
import com.example.compositiongame.domain.entity.GameSettings
import com.example.compositiongame.domain.entity.Level

class GameFragment : Fragment() {
    private lateinit var binding:FragmentGameBinding
    private lateinit var level: Level

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvOption1.setOnClickListener {
            launchFinishedFragment(GameResult(
                true,
                10,
                10,
                GameSettings(
                    10,
                    10,
                    10,
                    10
                )
            ))
        }

    }

    private fun launchFinishedFragment(gameResult: GameResult){
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container,FinishedFragment.newInstance(gameResult))
            .addToBackStack(null)
            .commit()
    }

    private fun parseArgs(){
        level = requireArguments().getParcelable<Level>(KEY_LEVEL) as Level
    }

    companion object {
        private const val KEY_LEVEL = "key_level"

        @JvmStatic
        fun newInstance(level: Level) =
            GameFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_LEVEL, level)
                }
            }
    }
}
package com.example.compositiongame.presentation

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.compositiongame.R
import com.example.compositiongame.data.GameRepositoryImpl
import com.example.compositiongame.domain.entity.GameSettings
import com.example.compositiongame.domain.entity.Level
import com.example.compositiongame.domain.entity.Question
import com.example.compositiongame.domain.usecase.GenerateQuestionUseCase
import com.example.compositiongame.domain.usecase.GetGameSettingsUseCase

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var level: Level
    private lateinit var gameSettings: GameSettings
    private val repository = GameRepositoryImpl

    private val context = application

    private val generateQuestionUseCase = GenerateQuestionUseCase(repository)
    private val getGameSettingsUseCase = GetGameSettingsUseCase(repository)

    private var timer: CountDownTimer? = null
    private val _formattedTime = MutableLiveData<String>()
    val formattedTime: LiveData<String>
        get() = _formattedTime

    private val _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _question

    private val _percentOfRightAnswers = MutableLiveData<Int>()
    val percentOfRightAnswers: LiveData<Int>
        get() = _percentOfRightAnswers

    private val _progressAnswers = MutableLiveData<String>()
    val progressAnswers: LiveData<String>
        get() = _progressAnswers

    private var countOfRightAnswers = 0
    private var countOfQuestions = 0

    fun startGame(level: Level) {
        getGameSettings(level)
        startTimer()
        generateQuestion()
    }

    fun chooseAnswer(number:Int){
        val rightAnswer = question.value?.rightAnswer
        if(rightAnswer==number){
            countOfRightAnswers++
        }
        countOfQuestions++
        generateQuestion()
    }

    private fun updateProgress(){
        val percent = calculatePercentOfRightAnswers()
        _percentOfRightAnswers.value = percent

    }

    private fun calculatePercentOfRightAnswers():Int{
        return (countOfRightAnswers/countOfQuestions.toDouble()*100).toInt()
    }

    private fun getGameSettings(level: Level) {
        this.level = level
        gameSettings = getGameSettingsUseCase(level)
    }

    private fun startTimer() {
        timer = object : CountDownTimer(
            gameSettings.gameTimeInSeconds * MILLIS_IN_SECONDS,
            MILLIS_IN_SECONDS
        ) {
            override fun onTick(millisUntilFinished: Long) {
                _formattedTime.value = formatTime(millisUntilFinished)
            }

            override fun onFinish() {
                finishGame()
            }
        }
        timer?.start()
    }

    private fun generateQuestion(){
        _question.value = generateQuestionUseCase(gameSettings.maxSumValue)

    }
    

    private fun formatTime(millisUntilFinished: Long): String {
        val seconds = millisUntilFinished / MILLIS_IN_SECONDS
        val minutes = seconds / SECONDS_IN_MINUTES
        val leftSeconds = seconds - (minutes * SECONDS_IN_MINUTES)
        return String.format("%02d:%02d", minutes, leftSeconds)
    }

    private fun finishGame() {
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }

    companion object {
        private const val MILLIS_IN_SECONDS = 1000L
        private const val SECONDS_IN_MINUTES = 60
    }

}
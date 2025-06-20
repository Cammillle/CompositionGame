package com.example.compositiongame.domain.repository

import com.example.compositiongame.domain.entity.GameSettings
import com.example.compositiongame.domain.entity.Level
import com.example.compositiongame.domain.entity.Question


interface GameRepository {

    fun generateQuestion(
        maxSumValue: Int,
        countOfOptions: Int
    ): Question

    fun getGameSettings(level: Level): GameSettings
}
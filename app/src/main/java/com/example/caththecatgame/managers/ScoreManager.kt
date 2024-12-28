package com.example.caththecatgame.managers

import android.content.Context
import android.content.SharedPreferences

class ScoreManager(context: Context) {

    private val PREFS_NAME = "best-scores"
    private val KEY_SCORES = "scores"

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun addScoreIfNeeded(newScore: Int) {
        val scores = getScores().toMutableList()

        if (scores.size < 10) {
            scores.add(newScore)
        } else {
            val minScore = scores.minOrNull()
            if (minScore != null && newScore > minScore) {
                scores.remove(minScore)
                scores.add(newScore)
            }
        }

        scores.sortDescending()
        saveScores(scores)
    }

    private fun getScores(): List<Int> {
        val scoresString = sharedPreferences.getString(KEY_SCORES, "") ?: ""
        if (scoresString.isEmpty()) {
            return emptyList()
        }
        return scoresString.split(",").map { it.toInt() }
    }

    private fun saveScores(scores: List<Int>) {
        val scoresString = scores.joinToString(",")
        sharedPreferences.edit().putString(KEY_SCORES, scoresString).apply()
    }

    fun getBestScores(): List<Int> {
        return getScores()
    }
}
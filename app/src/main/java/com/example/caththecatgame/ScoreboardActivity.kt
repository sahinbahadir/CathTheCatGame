package com.example.caththecatgame

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import com.example.caththecatgame.databinding.ActivityScoreboardBinding
import com.example.caththecatgame.managers.ScoreManager

class ScoreboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScoreboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityScoreboardBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        val scoreManager = ScoreManager(this)
        var bestScores = scoreManager.getBestScores()

        writeBestScoresToScreen(bestScores)
    }

    fun returnMainActivity(view: View){
        finish()
    }

    private fun writeBestScoresToScreen(bestScores: List<Int>) {
        val constraintLayout = binding.main
        val constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayout)

        val scoreboardTextViewId = binding.scoreboardTextView.id
        var previousViewId = scoreboardTextViewId

        for (score in bestScores) {
            val textView = TextView(this).apply {
                id = TextView.generateViewId()
                text = score.toString()
                textSize = 40f
                setTextColor(ContextCompat.getColor(this@ScoreboardActivity, R.color.black))
                setPadding(10, 10, 10, 10)
            }

            constraintLayout.addView(textView)

            constraintSet.constrainWidth(textView.id, ConstraintSet.WRAP_CONTENT)
            constraintSet.constrainHeight(textView.id, ConstraintSet.WRAP_CONTENT)

            constraintSet.connect(textView.id, ConstraintSet.TOP, previousViewId, ConstraintSet.BOTTOM, 10)
            constraintSet.connect(textView.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0)
            constraintSet.connect(textView.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 0)

            previousViewId = textView.id
        }

        constraintSet.applyTo(constraintLayout)
    }
}
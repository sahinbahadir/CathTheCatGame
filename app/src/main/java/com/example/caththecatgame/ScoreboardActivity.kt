package com.example.caththecatgame

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.caththecatgame.databinding.ActivityScoreboardBinding

class ScoreboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScoreboardBinding
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityScoreboardBinding.inflate(layoutInflater)
        val view = binding.root
        var bestScore = sharedPref.getInt("bestScore", 0)
        binding.bestScoreText.setText(bestScore.toString())

        setContentView(view)

        sharedPref = this.getSharedPreferences("com.example.catchthecat", MODE_PRIVATE)

    }

    fun returnMainActivity(view: View){
        finish()
    }

}
package com.example.caththecatgame

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.caththecatgame.databinding.ActivityMainBinding
import com.example.caththecatgame.managers.ScoreManager
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var scoreManager: ScoreManager
    private lateinit var binding: ActivityMainBinding
    private var runnable: Runnable = Runnable{}
    private var runnableImagePosition: Runnable = Runnable{}
    private var handler: Handler = Handler(Looper.getMainLooper())
    private var score: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.catView.isClickable = false
        binding.catView.visibility = View.INVISIBLE
        scoreManager = ScoreManager(this)
    }

    fun goToScoreboardAct(view : View){
        val intent = Intent().setClass(this@MainActivity, ScoreboardActivity::class.java)
        startActivity(intent)
    }

    fun startCatGame(view: View){
        binding.restartButton.setText("Restart")
        binding.restartButton.isClickable = false
        binding.scoreTextView.setText("Score: ${score}")
        binding.catView.visibility = View.VISIBLE

        binding.catView.isClickable = true

        var number = 10
        runnable = object: Runnable {
            override fun run() {
                number -= 1
                binding.timeTextView.setText("Time: ${number}")
                handler.postDelayed(runnable, 1000)
                if (number == 0) {
                    handler.removeCallbacks(runnable)
                    handler.removeCallbacks(runnableImagePosition)

                    scoreManager.addScoreIfNeeded(newScore = score)

                    binding.catView.isClickable = false
                    binding.catView.visibility = View.INVISIBLE
                    var restartAlert = restartGameAlert()
                    restartAlert.show()
                    score = 0
                    binding.restartButton.isClickable = true
                }
            }
        }

        runnableImagePosition = object : Runnable{
            override fun run() {
                var leftPadding = Random.nextInt(0, binding.gameLayout.width-binding.catView.width)
                var topPadding = Random.nextInt(0, binding.gameLayout.height-binding.catView.height)
                binding.gameLayout.setPadding(leftPadding, topPadding, 0, 0)
                handler.postDelayed(runnableImagePosition, 500)
            }
        }

        handler.post(runnable)
        handler.post(runnableImagePosition)
    }

    fun increaseScore(view: View){
        score += 1
        binding.scoreTextView.setText("Score: ${score}")
    }


    fun restartGameAlert() : AlertDialog.Builder{
        val alert = AlertDialog.Builder(this@MainActivity)
        alert.setTitle("Game Over")
        alert.setMessage("You scored: ${score}")
        alert.setPositiveButton("Restart", object : DialogInterface.OnClickListener{
            override fun onClick(p0: DialogInterface?, p1: Int) {
                Toast.makeText(this@MainActivity, "Restarted.", Toast.LENGTH_LONG).show()
                binding.restartButton.performClick()
            }
        })
        alert.setNegativeButton("Return") { p0, p1 ->
            Toast.makeText(this@MainActivity, "Returned the main page.", Toast.LENGTH_LONG).show()
        }
        return alert
    }

}
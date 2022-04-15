package com.example.tictactoeexample

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlin.properties.Delegates

// 그래 오늘 편하게 그냥 Tic Tac Toe 하는것만 배우자.. 천천히 해라.. 정확히 아는게 중요한 거니깐...
class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var buttons = arrayOf<Array<Button?>>(arrayOfNulls(3), arrayOfNulls(3), arrayOfNulls(3))
    private var buttons2 = Array(3) { arrayOfNulls<Button>(3) }
    private var player1Turn : Boolean = true
    private var roundCount : Int = 0
    private var player1Points : Int = 0
    private var player2Points : Int = 0
    private lateinit var textViewPlayer1 : TextView
    private lateinit var textViewPlayer2 : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "Tic Tac Toe"
        textViewPlayer1 = findViewById(R.id.text_view_p1)
        textViewPlayer2 = findViewById(R.id.text_view_p2)
        buttonFVI()
        val buttonReset : Button = findViewById(R.id.button_reset)
        buttonReset.setOnClickListener {
            resetGame()
        }
    }

    private fun resetGame() {
        player1Points = 0
        player2Points = 0
        updatePointsText()
        resetBoard()
        //여기를 보니깐 멤버가 전부다 원래대로 돌아왔네..
        //그리고
    }

    private fun buttonFVI() {

        for (i in 0..2) {
            for (j in 0..2) {
                val buttonId = "button_$i$j"
                val resId = resources.getIdentifier(buttonId, "id", packageName)
                buttons2[i][j] = findViewById(resId)
                buttons2[i][j]?.setOnClickListener(this)
                buttons2[i][j]?.tag = "Button$i$j"
            }
        }
    }

    private fun draw() {
        AlertDialog.Builder(this)
            .setTitle("Tic Tac Toe")
            .setMessage("Nobody wins")
            .setPositiveButton("OK"
            ) { p0, p1 -> p0.cancel() }
            .create()
            .show()
        //Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show()
        resetBoard()
    }

    private fun player2Wins() {
        player2Points++
        updatePointsText()
        //Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_SHORT).show()
        AlertDialog.Builder(this)
            .setTitle("Tic Tac Toe")
            .setMessage("Player 2 wins! \nPlease check the score")
            .setPositiveButton("OK"
            ) { p0, p1 -> p0.cancel() }
            .create()
            .show()
        resetBoard()
    }

    private fun player1Wins() {
        player1Points++
        updatePointsText()
        //Toast.makeText(this, "Player 1 wins!", Toast.LENGTH_SHORT).show()
        AlertDialog.Builder(this)
            .setTitle("Tic Tac Toe")
            .setMessage("Player 1 wins! \nPlease check the score")
            .setPositiveButton("OK"
            ) { p0, p1 -> p0.cancel() }
            .create()
            .show()

        resetBoard()
    }

    private fun resetBoard() {
        for (i in 0..2) {
            for (j in 0..2) {
                buttons2[i][j]?.text = ""
            }
        }
        roundCount = 0
        player1Turn = true
    }

    private fun updatePointsText() {
        textViewPlayer1.text = "Player 1: $player1Points"
        textViewPlayer2.text = "Player 2 : $player2Points"
    }

    private fun checkForWin() : Boolean {
        var ox = arrayOf<Array<String?>>(arrayOfNulls(3), arrayOfNulls(3), arrayOfNulls(3))
        for (i in 0..2) {
            for (j in 0..2) {
                ox[i][j] = buttons2[i][j]?.text.toString()
            }
        }
        for (i in 0..2) {
            if (ox[i][0].equals(ox[i][1]) && ox[i][0].equals(ox[i][2]) && !ox[i][0].equals("")) {
                return true
            }
        }
        for (i in 0..2) {
            if (ox[0][i].equals(ox[1][i]) && ox[0][i].equals(ox[2][i]) && !ox[0][i].equals("")) {
                return true
            }
        }
        if (ox[0][0].equals(ox[1][1]) && ox[0][0].equals(ox[2][2]) && !ox[0][0].equals("")) {
            return true
        }
        if (ox[0][2].equals(ox[1][1]) && ox[0][2].equals(ox[2][0]) && !ox[0][2].equals("")) {
            return true
        }
        return false
    }

    override fun onClick(p0: View?) {

        //Toast.makeText(this@MainActivity, "${p0?.tag} 가 눌렸습니다.", Toast.LENGTH_SHORT).show()
        if (!(p0 as Button).text.toString().equals("")) {
            return
        }
            if (player1Turn) {
                (p0 as Button)?.text = "X"
            } else {
                (p0 as Button)?.text = "O"
            }
        roundCount++
        if (checkForWin()) {
            if (player1Turn) {
                player1Wins()
            } else {
                player2Wins()
            }
        } else if (roundCount == 9) {
            draw()
        } else {
            player1Turn = !player1Turn
        }

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        roundCount = savedInstanceState.getInt("roundCount")
        player1Points = savedInstanceState.getInt("player1Points")
        player2Points = savedInstanceState.getInt("player2Points")
        player1Turn = savedInstanceState.getBoolean("player1Turn")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("roundCount", roundCount)
        outState.putInt("player1Points", player1Points)
        outState.putInt("player2Points", player2Points)
        outState.putBoolean("player1Turn", player1Turn)
    }
}

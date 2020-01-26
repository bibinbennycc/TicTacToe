package com.example.tictactoe.feature

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.view.Gravity
import android.view.View
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.tictactoe.R
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var homeViewModel: HomeViewModel

    private val boardCells = Array(3) { arrayOfNulls<TextView>(3) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_main)
        homeViewModel = ViewModelProviders.of(this,viewModelFactory).get(HomeViewModel::class.java)
        setupBoard()
        setBoardObserver()
    }

    private fun setBoardObserver() {
        homeViewModel.boardModel.observe(this, Observer { board ->
            for (i in boardCells.indices) {
                for (j in boardCells.indices) {
                    boardCells[i][j]?.setText(board[i][j] ?: "")
                }
            }
        })
    }

    private fun setupBoard() {
        for (i in boardCells.indices) {
            for (j in boardCells.indices) {
                boardCells[i][j] = TextView(this)
                boardCells[i][j]?.layoutParams = GridLayout.LayoutParams().apply {
                    rowSpec = GridLayout.spec(i)
                    columnSpec = GridLayout.spec(j)
                    width = 250
                    height = 230
                    bottomMargin = 5
                    topMargin = 5
                    leftMargin = 5
                    rightMargin = 5
                }
                boardCells[i][j]?.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
                boardCells[i][j]?.setGravity(Gravity.CENTER)
                boardCells[i][j]?.setTextColor(ContextCompat.getColor(this,R.color.pure_white))
                boardCells[i][j]?.setOnClickListener(CellClickListener(i,j))
                layout_board.addView(boardCells[i][j])
            }
        }
    }

    inner class CellClickListener(private val i: Int, private val j: Int) : View.OnClickListener {
        override fun onClick(p0: View?) {

            if(homeViewModel.isCellSelected(homeViewModel.board, Cell(i,j))){
                Toast.makeText(this@MainActivity,"Please select another cell",Toast.LENGTH_SHORT).show()
                return
            }
            if (!homeViewModel.isGameOver) {

                //creating a new cell with the clicked index
                val cell = Cell(i, j)

                //placing the move for player
                homeViewModel.placeMove(cell, homeViewModel.PLAYER)

                //calling minimax to calculate the computers move
                homeViewModel.minimax(0, homeViewModel.COMPUTER)

                //performing the move for computer
                homeViewModel.computersMove?.let {
                    homeViewModel.placeMove(it, homeViewModel.COMPUTER)
                }
                homeViewModel.boardModel.value = homeViewModel.board
            }

           val message = when {
                homeViewModel.hasComputerWon(homeViewModel.board) -> "Computer Won"
                homeViewModel.hasPlayerWon(homeViewModel.board) ->  "Player Won"
                homeViewModel.isGameOver -> "Game Tied"
                else -> null
            }

            message?.let {
                homeViewModel.resetGame()
                Toast.makeText(applicationContext,message,Toast.LENGTH_LONG).show()
            }
        }
    }
}

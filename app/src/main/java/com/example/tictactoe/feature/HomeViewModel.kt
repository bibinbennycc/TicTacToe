package com.example.tictactoe.feature

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class HomeViewModel @Inject constructor(val repository: HomeRepository) : ViewModel() {

    var board = Array(3) { arrayOfNulls<String>(3) }
    var boardModel = MutableLiveData<Array<Array<String?>>>()
    val PLAYER = "O"
    val COMPUTER = "X"

    val availableCells: List<Cell>
        get() {
            val cells = mutableListOf<Cell>()
            for (i in board.indices) {
                for (j in board.indices) {
                    if (board[i][j].isNullOrEmpty()) {
                        cells.add(Cell(i, j))
                    }
                }
            }
            return cells
        }

    //this property will tell
    //if the game is over or not
    val isGameOver: Boolean
        get() = hasComputerWon(board) || hasPlayerWon(board) || availableCells.isEmpty()


    //These functions are checking
    //Weather the computer or player has won or not
    fun hasComputerWon(board: Array<Array<String?>>): Boolean {
        if (board[0][0] == board[1][1] &&
            board[0][0] == board[2][2] &&
            board[0][0] == COMPUTER ||
            board[0][2] == board[1][1] &&
            board[0][2] == board[2][0] &&
            board[0][2] == COMPUTER
        ) {
            return true
        }

        for (i in board.indices) {
            if (
                board[i][0] == board[i][1] &&
                board[i][0] == board[i][2] &&
                board[i][0] == COMPUTER ||
                board[0][i] == board[1][i] &&
                board[0][i] == board[2][i] &&
                board[0][i] == COMPUTER
            ) {
                return true
            }
        }

        return false
    }

    fun hasPlayerWon(board: Array<Array<String?>>): Boolean {

        if (board[0][0] == board[1][1] &&
            board[0][0] == board[2][2] &&
            board[0][0] == PLAYER ||
            board[0][2] == board[1][1] &&
            board[0][2] == board[2][0] &&
            board[0][2] == PLAYER
        ) {
            return true
        }

        for (i in board.indices) {
            if (
                board[i][0] == board[i][1] &&
                board[i][0] == board[i][2] &&
                board[i][0] == PLAYER ||
                board[0][i] == board[1][i] &&
                board[0][i] == board[2][i] &&
                board[0][i] == PLAYER
            ) {
                return true
            }
        }

        return false
    }


    //in this var we will store the computersMove
    var computersMove: Cell? = null

    //this is our minimax function to calculate
    //the best move for the computer
    fun minimax(depth: Int, player: String): Int {
        if (hasComputerWon(board)) return +1
        if (hasPlayerWon(board)) return -1

        if (availableCells.isEmpty()) return 0

        var min = Integer.MAX_VALUE
        var max = Integer.MIN_VALUE

        for (i in availableCells.indices) {
            val cell = availableCells[i]
            if (player == COMPUTER) {
                placeMove(cell, COMPUTER)
                val currentScore = minimax(depth + 1, PLAYER)
                max = Math.max(currentScore, max)

                if (currentScore >= 0) {
                    if (depth == 0) computersMove = cell
                }

                if (currentScore == 1) {
                    board[cell.row][cell.col] = ""
                    break
                }

                if (i == availableCells.size - 1 && max < 0) {
                    if (depth == 0) computersMove = cell
                }

            } else if (player == PLAYER) {
                placeMove(cell, PLAYER)
                val currentScore = minimax(depth + 1, COMPUTER)
                min = Math.min(currentScore, min)

                if (min == -1) {
                    board[cell.row][cell.col] = ""
                    break
                }
            }
            board[cell.row][cell.col] = ""
        }

        return if (player == COMPUTER) max else min
    }

    fun placeMove(cell: Cell, player: String) {
        board[cell.row][cell.col] = player
    }

    fun resetGame() {
        board = Array(3) { arrayOfNulls<String>(3) }
        boardModel.value = board
    }

    fun isCellSelected(board: Array<Array<String?>>, cell: Cell):Boolean{
        val value = board[cell.row][cell.col]?.isNullOrEmpty() ?: true
        return !value
    }
}
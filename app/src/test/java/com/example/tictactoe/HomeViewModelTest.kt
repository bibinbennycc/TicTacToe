package com.example.tictactoe

import com.example.tictactoe.feature.Cell
import com.example.tictactoe.feature.HomeRepository
import com.example.tictactoe.feature.HomeViewModel
import dagger.android.AndroidInjection
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import javax.inject.Inject

@RunWith(value = JUnit4::class)
class HomeViewModelTest {

    lateinit var viewModel: HomeViewModel
    lateinit var repo: HomeRepository

    @Before
    fun setUp(){
        repo = HomeRepository()
        viewModel = HomeViewModel(repo)
    }

    @Test
    fun test_hasPlayerWon(){
        val board1 = arrayOf(arrayOfNulls<String>(3), kotlin.arrayOfNulls<String>(3), arrayOfNulls<String>(3))
        val board2 = arrayOf(arrayOf("X","O",null), arrayOf("X",null,"O"), arrayOf("X",null,null))
        val board3 = arrayOf(arrayOf("X","O",null), arrayOf("X","O",null), arrayOf("X","O",null))
        val board4 = arrayOf(arrayOf("O","O",null), arrayOf("X","O",null), arrayOf("X",null,"O"))
        val board5 = arrayOf(arrayOf(null,"X","O"), arrayOf("X","O",null), arrayOf("O","O",null))
        val board6 = arrayOf(arrayOf("O","X",null), arrayOf("O","X",null), arrayOf(null,"X",null))
        Assert.assertEquals(viewModel.hasPlayerWon(board1),false)
        Assert.assertEquals(viewModel.hasPlayerWon(board2),false)
        Assert.assertEquals(viewModel.hasPlayerWon(board6),false)
        Assert.assertEquals(viewModel.hasPlayerWon(board3),true)
        Assert.assertEquals(viewModel.hasPlayerWon(board4),true)
        Assert.assertEquals(viewModel.hasPlayerWon(board5),true)
    }

    @Test
    fun test_hasComputerWon(){
        val board1 = arrayOf(arrayOfNulls<String>(3), kotlin.arrayOfNulls<String>(3), arrayOfNulls<String>(3))
        val board2 = arrayOf(arrayOf("O","X",null), arrayOf("O",null,"X"), arrayOf("O",null,null))
        val board3 = arrayOf(arrayOf("O","X",null), arrayOf("O","X",null), arrayOf("O","X",null))
        val board4 = arrayOf(arrayOf("X","X",null), arrayOf("O","X",null), arrayOf("O",null,"X"))
        val board5 = arrayOf(arrayOf(null,"O","X"), arrayOf("O","X",null), arrayOf("X","X",null))
        val board6 = arrayOf(arrayOf("X","O",null), arrayOf("X","O",null), arrayOf(null,"O",null))
        Assert.assertEquals(viewModel.hasComputerWon(board1),false)
        Assert.assertEquals(viewModel.hasComputerWon(board2),false)
        Assert.assertEquals(viewModel.hasComputerWon(board6),false)
        Assert.assertEquals(viewModel.hasComputerWon(board3),true)
        Assert.assertEquals(viewModel.hasComputerWon(board4),true)
        Assert.assertEquals(viewModel.hasComputerWon(board5),true)
    }

    @Test
    fun test_isCellSelected(){
        val board1 = arrayOf(arrayOfNulls<String>(3), kotlin.arrayOfNulls<String>(3), arrayOfNulls<String>(3))
        val board2 = arrayOf(arrayOf("O","X",null), arrayOf("O",null,"X"), arrayOf("O",null,null))
        val board3 = arrayOf(arrayOf("O","X",null), arrayOf("O","X",null), arrayOf("O","X",null))
        val board4 = arrayOf(arrayOf("X","X",null), arrayOf("O","X",null), arrayOf("O",null,"X"))
        val board5 = arrayOf(arrayOf(null,"O","X"), arrayOf("O","X",null), arrayOf("X","X",null))
        val board6 = arrayOf(arrayOf("X","O",null), arrayOf("X","O",null), arrayOf(null,"O",null))
        Assert.assertEquals(viewModel.isCellSelected(board1, Cell(0,0)),false)
        Assert.assertEquals(viewModel.isCellSelected(board2, Cell(0,2)),false)
        Assert.assertEquals(viewModel.isCellSelected(board6, Cell(2,2)),false)
        Assert.assertEquals(viewModel.isCellSelected(board3, Cell(0,0)),true)
        Assert.assertEquals(viewModel.isCellSelected(board4, Cell(1,0)),true)
        Assert.assertEquals(viewModel.isCellSelected(board5, Cell(2,1)),true)
    }
}
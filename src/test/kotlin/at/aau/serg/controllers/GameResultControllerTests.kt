package at.aau.serg.controllers

import at.aau.serg.models.GameResult
import at.aau.serg.services.GameResultService
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import kotlin.test.Test
import kotlin.test.assertEquals
import org.mockito.Mockito.`when` as whenever // when is a reserved keyword in Kotlin

class GameResultControllerTests {

    private lateinit var mockedService: GameResultService
    private lateinit var controller: GameResultController

    @BeforeEach
    fun setup() {
        mockedService = mock<GameResultService>()
        controller = GameResultController(mockedService)
    }

    @Test
    fun test_getGameResult_returnsGameResult() {
        val gameResult = GameResult(1, "player1", 50, 20.0)

        whenever(mockedService.getGameResult(1)).thenReturn(gameResult)
        val result = controller.getGameResult(1)

        verify(mockedService).getGameResult(1)
        assertEquals(gameResult, result)
    }

    @Test
    fun test_getAllGameResults_returnsWholeListGameResult() {
        val a = GameResult(1, "A", 100, 20.0)
        val b = GameResult(2, "B", 90, 15.0)
        val c = GameResult(3, "C", 80, 14.0)

        whenever(mockedService.getGameResults()).thenReturn(listOf(a, b, c))

        val list = listOf(a, b, c)
        val result = controller.getAllGameResults()

        verify(mockedService).getGameResults()
        assertEquals(list, result)
    }

    @Test
    fun test_addGameResult() {
        val gameResult = GameResult(0, "player1", 25, 18.0)

        controller.addGameResult(gameResult)

        verify(mockedService).addGameResult(gameResult)
    }

    @Test
    fun test_deleteGameResult() {
        controller.deleteGameResult(5)

        verify(mockedService).deleteGameResult(5)
    }
}

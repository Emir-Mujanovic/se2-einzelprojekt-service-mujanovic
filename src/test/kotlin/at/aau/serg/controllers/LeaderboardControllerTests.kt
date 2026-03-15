package at.aau.serg.controllers

import at.aau.serg.models.GameResult
import at.aau.serg.services.GameResultService
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.springframework.web.server.ResponseStatusException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import org.mockito.Mockito.`when` as whenever // when is a reserved keyword in Kotlin

class LeaderboardControllerTests {

    private lateinit var mockedService: GameResultService
    private lateinit var controller: LeaderboardController

    @BeforeEach
    fun setup() {
        mockedService = mock<GameResultService>()
        controller = LeaderboardController(mockedService)
    }

    @Test
    fun test_getLeaderboard_correctScoreSortingRankNOtThere() {
        val a = GameResult(1, "A", 100, 20.0)
        val b = GameResult(2, "B", 90, 15.0)
        val c = GameResult(3, "C", 90, 14.0)

        whenever(mockedService.getGameResults()).thenReturn(listOf(a, b, c))

        val res = controller.getLeaderboard(null)

        verify(mockedService).getGameResults()
        assertEquals(listOf(a, c, b), res)
    }

    @Test
    fun test_getLeaderboard_rankAroundPlayers() {
        val players = listOf(
            GameResult(1, "Anna", 150, 32.5),
            GameResult(2, "Chris", 150, 35.0),
            GameResult(3, "Eli", 140, 33.5),
            GameResult(4, "Ben", 120, 30.0),
            GameResult(5, "Dora", 100, 28.0),
            GameResult(6, "Muje", 100, 28.0),
        )

        whenever(mockedService.getGameResults()).thenReturn(players)

        val res = controller.getLeaderboard(5) // Rank = 5

        assertEquals(listOf(players[1], players[2], players[3], players[4], players[5]), res)


    }
    @Test
    fun test_getLeaderboard_invalidRank() {
        whenever(mockedService.getGameResults())
            .thenReturn(emptyList())

        assertFailsWith<ResponseStatusException> {
            controller.getLeaderboard(-1)
        }
    }

    @Test
    fun test_getLeaderboard_sameScore_CorrectIdSorting() {
        val first = GameResult(1, "first", 20, 9.0)
        val second = GameResult(2, "second", 20, 10.0)
        val third = GameResult(3, "third", 20, 15.0)

        whenever(mockedService.getGameResults()).thenReturn(listOf(first, second, third))

        val res: List<GameResult> = controller.getLeaderboard(null)

        verify(mockedService).getGameResults()
        assertEquals(3, res.size)
        assertEquals(first, res[0])
        assertEquals(second, res[1])
        assertEquals(third, res[2])
    }

    @Test
    fun test_getLeaderboard_rankTooHigh_throwsBadRequest() {
        val first = GameResult(1, "first", 20, 9.0)
        val second = GameResult(2, "second", 20, 10.0)
        val third = GameResult(3, "third", 20, 15.0)

        whenever(mockedService.getGameResults()).thenReturn(listOf(first, second, third))

        //es gibt nur 3 Spieler, rang 5 ist zu hoch/OutofBounds
        assertFailsWith<ResponseStatusException> {
            controller.getLeaderboard(5)
        }
    }
}

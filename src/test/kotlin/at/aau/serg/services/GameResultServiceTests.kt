package at.aau.serg.services

import at.aau.serg.models.GameResult
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class GameResultServiceTests {

    private lateinit var service: GameResultService

    @BeforeEach
    fun setup() {
        service = GameResultService()
    }

    @Test
    fun test_getGameResults_ListExists() {
        val result = service.getGameResults()

        assertEquals(5, result.size)
    }

    @Test
    fun test_addGameResult_getGameResults_containsSingleElement() {
        val res = service.getGameResults()

        assertEquals(5, res.size)
    }

    @Test
    fun test_getGameResultById_existingId_returnsObject() {
        val gameResult = GameResult(0, "player1", 17, 15.3)

        service.addGameResult(gameResult)
        val res = service.getGameResult(6)

        assertEquals(gameResult, res)
    }

    @Test
    fun test_getGameResultById_nonexistentId_returnsNull() {
        val gameResult = GameResult(1, "player1", 17, 15.3)
        service.addGameResult(gameResult)

        val res = service.getGameResult(22)

        assertNull(res)
    }

    @Test
    fun test_addGameResult_multipleEntries_correctId() {
        val gameResult1 = GameResult(0, "player1", 17, 15.3)
        val gameResult2 = GameResult(0, "player2", 25, 16.0)

        service.addGameResult(gameResult1)
        service.addGameResult(gameResult2)

        val res = service.getGameResults()

        assertEquals(7, res.size)

        assertEquals(gameResult1, res[5])
        assertEquals(6, res[5].id)

        assertEquals(gameResult2, res[6])
        assertEquals(7, res[6].id)
    }

    @Test
    fun test_deleteGameResult_existingId_removesElement() {
        service.deleteGameResult(1)

        val res = service.getGameResults()

        assertEquals(4, res.size)
    }

}
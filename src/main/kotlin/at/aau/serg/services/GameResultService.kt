package at.aau.serg.services

import at.aau.serg.models.GameResult
import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicLong

@Service
class GameResultService {

    private val gameResults = mutableListOf<GameResult>()
    private var nextId = AtomicLong(1)

    /*init {
        addGameResult(GameResult(0, "Anna", 150, 32.5))
        addGameResult(GameResult(0, "Chris", 150, 35.0))
        addGameResult(GameResult(0, "Eli", 140, 33.5))
        addGameResult(GameResult(0, "Ben", 120, 30.0))
        addGameResult(GameResult(0, "Dora", 100, 28.0))
    }*/

        fun addGameResult(gameResult: GameResult) {
        gameResult.id = nextId.getAndIncrement()
        gameResults.add(gameResult)
    }

    fun getGameResult(id: Long): GameResult? = gameResults.find { it.id == id } // ? allows null

    fun getGameResults(): List<GameResult> = gameResults.toList() // returns immutable list copy

    /**
     * Kotlin-idiomatic for:
     * fun deleteGameResult(gameResultId: Long) {
     *     gameResults.removeIf({ gameResult -> gameResult.id == gameResultId })
     * }
     */
    fun deleteGameResult(id: Long) = gameResults.removeIf { it.id == id }

}
package at.aau.serg.controllers

import at.aau.serg.models.GameResult
import at.aau.serg.services.GameResultService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import org.springframework.http.HttpStatus


@RestController
@RequestMapping("/leaderboard")
class LeaderboardController(
    private val gameResultService: GameResultService
) {

    @GetMapping
    fun getLeaderboard(@RequestParam(required = false) rank: Int?): List<GameResult>{
        val leaderboardSorted = gameResultService.getGameResults().sortedWith(compareBy({ -it.score },
            { it.timeInSeconds }))

        //ungültiger rank, ganze Liste wird ausgegeben
        if(rank == null){
            return leaderboardSorted
        }

        //ungültiger rank, Fehler HTTP 400 wird angezeigt
        if(rank < 1 || rank > leaderboardSorted.size){
            throw ResponseStatusException(HttpStatus.BAD_REQUEST)
        }

        val index = rank - 1  //Ränge starten bei 1, Indexe einer Liste jedoch bei 0
        val start = maxOf(0, index - 3) //3 Spieler vor dem Rang
        val end = minOf(leaderboardSorted.size, index + 4) //3 Spieler über dem rang, bei +3 werden nur 2 Spieler drüber angezeigt

        return leaderboardSorted.subList(start, end) //gibt die Spieler um den Rang zurück +-3
    }



}
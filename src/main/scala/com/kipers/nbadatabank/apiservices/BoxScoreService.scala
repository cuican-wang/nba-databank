package com.kipers.nbadatabank.apiservices

import com.kipers.nbadatabank.apiservices.BoxScoreSteamType.BoxScoreSteamType
import com.kipers.nbadatabank.common.StatsAPI
import com.kipers.nbadatabank.common.Types.NbaResult
import rx.lang.scala.Observable

import scala.concurrent.ExecutionContext

object BoxScoreSteamType extends Enumeration {
  type BoxScoreSteamType = Value
  val PlayerStats, GameSummary, LineScore, SeasonSeries, LastMeeting, TeamStats, OtherStats, Officials, GameInfo,
  InactivePlayers, PlayerTrack, PlayerTrackTeam = Value
}

object BoxScoreService {
  val Endpoint = "boxscore"

  val defaultRequestParams =
    Map("StartRange" -> "0", "EndRange" -> "0", "StartPeriod" -> "0", "EndPeriod" -> "0", "RangeType" -> "0")

  def getBoxScoreStreams(gameId: String, delayInMillis: Int = 0)(implicit exec: ExecutionContext): Observable[(String, List[NbaResult])] = {
    val params = defaultRequestParams + ("GameId" -> gameId)
    StatsAPI.get(Endpoint, params, delayInMillis)
  }

  def getBoxScoreStream(boxScoreStreams: Observable[(String, List[NbaResult])], boxScoreStreamType: BoxScoreSteamType): Observable[NbaResult] = {
    StatsAPI.getResultStreamFromRequestStream(boxScoreStreams, boxScoreStreamType.toString)
  }
}

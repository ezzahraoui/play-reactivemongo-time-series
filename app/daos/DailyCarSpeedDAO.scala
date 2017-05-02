package daos

import javax.inject._
import java.util.UUID
import org.joda.time.DateTime
import play.api.libs.json._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import play.modules.reactivemongo._
import reactivemongo.api._
import play.modules.reactivemongo.json._
import reactivemongo.play.json.collection.JSONCollection
import reactivemongo.api.commands.UpdateWriteResult
import reactivemongo.play.json.commands.JSONAggregationFramework._

object DailyCarSpeedDAO {
  case class FindQuery(
    startDate: String,
    endDate: String)
  case class UpdateQuery(
    date: String)
  case class Updater(
    date: String,
    hour: Int,
    minute: Int,
    value: Int)
  object UpdateQuery {
    implicit val jsonFormat = Json.format[UpdateQuery]
  }
}

class DailyCarSpeedDAO @Inject() (val reactiveMongoApi: ReactiveMongoApi) {
  
  import models._
  import DailyCarSpeedDAO._

  def collection: Future[JSONCollection] = reactiveMongoApi.database.map(_.collection("speed.daily"))

  def save(dailyCarSpeed: DailyCarSpeed): Future[Either[String, UUID]] = {
    collection.flatMap(_.insert(dailyCarSpeed).map {
      case result if result.ok == true => Right(dailyCarSpeed._id)
      case result => Left(result.writeErrors(0).errmsg)
    })
  }

  def update(query: UpdateQuery, updater: Updater): Future[UpdateWriteResult] = {
    val update = Json.obj("$set" -> Json.obj(s"hours.${updater.hour}.minutes.${updater.minute}.value" -> updater.value))
    collection.flatMap(_.update(query, update))
  }

  def find(findQuery: FindQuery): Future[List[JsObject]] = {
    val startDateTime = DateTime.parse(findQuery.startDate)
    val startDay = startDateTime.minusMillis(startDateTime.getMillisOfDay)

    val endDateTime = DateTime.parse(findQuery.endDate)
    val endDay = endDateTime.minusMillis(endDateTime.getMillisOfDay)

    val match1 = Match(Json.obj(
      "date" -> Json.obj(
        "$gte" -> startDay.toString,
        "$lte" -> endDay.toString)))
    val match2 = Match(Json.obj("hours.minutes.date" -> Json.obj("$gte" -> findQuery.startDate, "$lte" -> findQuery.endDate)))
    val project = Project(Json.obj("date" -> "$hours.minutes.date", "value" -> "$hours.minutes.value", "_id" -> 0))
    val sort = Sort(Ascending("date"))
    collection.flatMap(_.aggregate(match1, List(UnwindField("hours"), UnwindField("hours.minutes"), match2, project, sort)).map(_.firstBatch))
  }

}
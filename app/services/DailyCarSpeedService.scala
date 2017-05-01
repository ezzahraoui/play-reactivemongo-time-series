package services

import javax.inject.Inject
import org.joda.time.LocalDateTime
import java.util.UUID
import scala.util.Random
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.collection.mutable.ListBuffer
import play.api.libs.json._
import daos._
import models._

object DailyCarSpeedService {
  case class Request(
    _id: UUID,
    date: LocalDateTime,
    value: Int)
}

class DailyCarSpeedService @Inject() (dao: DailyCarSpeedDAO) {
  import DailyCarSpeedService._
  import DailyCarSpeedDAO._
  import models._

  def find(findQuery: FindQuery): Future[List[JsObject]] = {
    dao.find(findQuery)
  }

  def save(request: Request) = {
    val (query, updater) = createUpdate(request)
    dao.update(query, updater).map {
      // Insert new document if none was updated
      case result if result.nModified == 0 =>
        val dailyCarSpeed = createInsert(request)
        dao.save(dailyCarSpeed).map {
          case Right(id) =>
            // Update document after insert 
            dao.update(query, updater).map {
              case result if result.ok == true =>
              case result =>
            }
          case Left(err) =>
        }
      // It means that the document is updated successfully
      case result if result.nModified > 0 =>
    }
  }

  // Create DailyCarSpeed from service request
  private def createInsert(request: Request): DailyCarSpeed = {
    val day = request.date.minusMillis(request.date.getMillisOfDay)
    val hours = ListBuffer[HourlyCarSpeed]()
    for (hour <- 0 to 23) {
      val currentHour = day.plusHours(hour)
      val minutes = ListBuffer[MinutelyCarSpeed]()
      for (minute <- 0 to 59) {
        minutes += MinutelyCarSpeed(
          date = currentHour.plusMinutes(minute).toString,
          value = 0)
      }
      hours += HourlyCarSpeed(
        minutes = minutes)
    }
    DailyCarSpeed(request._id, day.toString, hours)
  }

  // Create update query and updater from service request
  private def createUpdate(request: Request): Tuple2[UpdateQuery, Updater] = {
    val day = request.date.minusMillis(request.date.getMillisOfDay)
    val hour = request.date.getHourOfDay()
    // TODO check returned value
    val minute =  0 //request.date.getMinuteOfDay()
    val query = UpdateQuery(request._id, day.toString)
    val updater = Updater(request.date.toString, hour, minute, request.value)
    (query, updater)
  }
}
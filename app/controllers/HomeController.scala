package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.libs.json._
import org.joda.time.LocalDateTime
import java.util.UUID
import services._
import daos.DailyCarSpeedDAO._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class HomeController @Inject() (service: DailyCarSpeedService) extends Controller {

  def index = Action {
    Ok(Json.obj("name" -> "play2 reactivemongo time series example", "version" -> "1.0")).as("application/json")
  }

  def findLastDay = Action.async { request =>
    val _id = UUID.fromString("8ec5c456-78ca-4858-bf95-3448e05ae52a")
    val endDate = new LocalDateTime()
    val startDate = endDate.minusHours(24)
    val findQuery = FindQuery(_id, startDate.toString, endDate.toString)
    service.find(findQuery).map((list: List[JsObject]) => Ok(Json.toJson(list)))
  }

}

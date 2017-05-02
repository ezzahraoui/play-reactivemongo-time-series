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

  def index = Action.async {
    Future.successful(Ok(views.html.index("Home")))
  }

  def findLastDay = Action.async { request =>
    val endDate = new LocalDateTime()
    val startDate = endDate.minusHours(24)
    val findQuery = FindQuery(startDate.toString, endDate.toString)
    service.find(findQuery).map((list: List[JsObject]) => Ok(Json.toJson(list)))
  }

  def findLastDayByAbbr(abbreviation: String) = Action.async { request =>
    val endDate = new LocalDateTime()
    val startDate = endDate.minusHours(24)
    val findQuery = FindQuery(startDate.toString, endDate.toString)
    service.find(findQuery).map((list: List[JsObject]) => Ok(Json.toJson(list)))
  }

  private def getZone(abbreviation: String) = {
  	abbreviation match {
  		case "PST" => "America/Los_Angeles"
  		case "CET" => "Europe/Paris"
  		case _ => "Africa/Casablanca"
  	}
  }
}

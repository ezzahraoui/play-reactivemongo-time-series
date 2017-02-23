package models

import java.util.UUID
import org.joda.time.DateTime
import play.api.libs.json._

case class DailyCarSpeed(
  _id: UUID,
  date: String,
  hours: Seq[HourlyCarSpeed])

case class HourlyCarSpeed(
  date: String,
  minutes: Seq[MinutelyCarSpeed])

case class MinutelyCarSpeed(
  date: Option[String],
  value: Option[Int])

object MinutelyCarSpeed {
  implicit val jsonFormat = Json.format[MinutelyCarSpeed]
}
object HourlyCarSpeed {
  implicit val jsonFormat = Json.format[HourlyCarSpeed]
}
object DailyCarSpeed {
  implicit val jsonFormat = Json.format[DailyCarSpeed]
}
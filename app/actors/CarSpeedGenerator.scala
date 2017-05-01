package actors

import javax.inject.Inject
import akka.actor._
import org.joda.time.LocalDateTime
import scala.util.Random
import java.util.UUID
import services._

object CarSpeedGenerator {
  case object Update
}

class CarSpeedGenerator @Inject() (service: DailyCarSpeedService) extends Actor {
  import CarSpeedGenerator._
  import services.DailyCarSpeedService._

  val _id = UUID.fromString("8ec5c456-78ca-4858-bf95-3448e05ae52a")

  def receive: Receive = {
    case Update =>
      val speed = Random.nextInt(200)
      val date = new LocalDateTime()
      service.save(Request(_id, date, speed))
  }
}

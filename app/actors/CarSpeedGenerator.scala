package actors

import javax.inject.Inject
import akka.actor._
import org.joda.time.LocalDateTime
import scala.util.Random
import services._

object CarSpeedGenerator {
  case object Update
}

class CarSpeedGenerator @Inject() (service: DailyCarSpeedService) extends Actor {
  import CarSpeedGenerator._
  import services.DailyCarSpeedService._

  def receive: Receive = {
    case Update =>
      val speed = Random.nextInt(200)
      val date = new LocalDateTime()
      service.save(Request(date, speed))
  }
}

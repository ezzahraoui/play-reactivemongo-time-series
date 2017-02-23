import com.google.inject.AbstractModule
import net.codingwell.scalaguice.ScalaModule
import play.api.libs.concurrent.AkkaGuiceSupport
import daos._
import services._
import actors._

class Module extends AbstractModule with AkkaGuiceSupport with ScalaModule {

  override def configure() = {
  	bindActor[CarSpeedGenerator]("car-speed-generator")
    bind[Scheduler].asEagerSingleton()
    bind[DailyCarSpeedService].asEagerSingleton()
    bind[DailyCarSpeedDAO].asEagerSingleton()
  }

}

package actors

import akka.actor.{ ActorRef, ActorSystem }
import com.google.inject.Inject
import com.google.inject.name.Named
import com.typesafe.akka.extension.quartz.QuartzSchedulerExtension

class Scheduler @Inject() (system: ActorSystem, @Named("car-speed-generator") carSpeedGenerator: ActorRef) {
  
  QuartzSchedulerExtension(system).schedule("CarSpeedGenerator", carSpeedGenerator, CarSpeedGenerator.Update)

  carSpeedGenerator ! CarSpeedGenerator.Update

}

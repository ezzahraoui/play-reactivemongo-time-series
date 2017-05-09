import org.scalatest._
import org.joda.time._

class DateTimeTestSpec extends FlatSpec with Matchers {
  "LocalDateTime getHourOfDay" should "be 21" in {
    val hours = LocalDateTime.parse("2017-05-01T21:30:45.000").getHourOfDay()
    hours should equal(21)
  }
  "LocalDateTime getMinuteOfHour" should "be 30" in {
    val minutes = LocalDateTime.parse("2017-05-01T21:30:45.000").getMinuteOfHour()
    minutes should equal(30)
  }
  "LocalDateTime getSecondOfMinute" should "be 45" in {
    val seconds = LocalDateTime.parse("2017-05-01T21:30:45.000").getSecondOfMinute()
    seconds should equal(45)
  }
  "LocalDateTime toLocalDate toDateTimeAtStartOfDay" should "be 2017-05-01T00:00:00.000" in {
    val datetime = LocalDateTime.parse("2017-05-01T21:30:45.000").toLocalDate().toDateTimeAtStartOfDay().toLocalDateTime()
    datetime should equal(new LocalDateTime("2017-05-01T00:00:00.000"))
  }
  "DateTime withZone Europe/Paris" should "be 1493668800000" in {
    val datetime = DateTime.parse("2017-05-01T21:00:00.000").withZone(DateTimeZone.forID("Europe/Paris")).getMillis()
    datetime should equal(1493668800000l)
  }
  "ISODate withZone Europe/Paris" should "be 2017-05-01T21:00:00.000+02:00" in {
    val datetime = LocalDateTime.parse("2017-05-01T21:00:00.000")
      .toDateTime(DateTimeZone.UTC)
      .withZone(DateTimeZone.forID("Europe/Paris"))
      .toString
    datetime should equal("2017-05-01T23:00:00.000+02:00")
  }
  "DateTime withZone America/Los_Angeles" should "be 1493668800000" in {
    val datetime = DateTime.parse("2017-05-01T21:00:00.000").withZone(DateTimeZone.forID("America/Los_Angeles")).getMillis()
    datetime should equal(1493668800000l)
  }
  "ISODate withZone America/Los_Angeles" should "be 2017-05-01T14:00:00.000-07:00" in {
    val datetime = LocalDateTime.parse("2017-05-01T21:00:00.000")
      .toDateTime(DateTimeZone.UTC)
      .withZone(DateTimeZone.forID("America/Los_Angeles"))
      .toString
    datetime should equal("2017-05-01T14:00:00.000-07:00")
  }
  "ISODate withZone America/Los_Angeles" should "be 2017-05-01T21:00:00.000 after parsing to LocalDateTime" in {
    val datetime = DateTime.parse("2017-05-01T14:00:00.000-07:00").toDateTime(DateTimeZone.UTC).toLocalDateTime().toString
    datetime should equal("2017-05-01T21:00:00.000")
  }
}
import cats.effect._
import natchez.Trace.Implicits.noop
import skunk._
import skunk.codec.all._
import skunk.implicits._

import java.time.OffsetDateTime

object QueryExample extends IOApp {

  val session: Resource[IO, Session[IO]] =
    Session.single(
      host = "localhost",
      user = "postgres",
      database = "saloespoc",
      password = Some("my-password")
    )

  // a data model
  case class Country(name: String, code: String, population: Int)

  // a simple query
  val simple: Query[Void, OffsetDateTime] =
    sql"select current_timestamp".query(timestamptz)

  // run our simple query
  def doSimple(s: Session[IO]): IO[Unit] =
    for {
      ts <- s.unique(simple) // we expect exactly one row
      _ <- IO.println(s"timestamp is $ts")
    } yield ()


  // our entry point
  def run(args: List[String]): IO[ExitCode] =
    session.use { s =>
      for {
        _ <- doSimple(s)
      } yield ExitCode.Success
    }

}

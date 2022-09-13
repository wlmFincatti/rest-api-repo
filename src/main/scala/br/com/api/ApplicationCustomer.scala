package br.com.api


import br.com.api.http.routes.CustomerRoutes
import br.com.api.repository.CustomerRepository
import br.com.api.repository.CustomerRepository.CustomerRepositoryImpl
import cats.data.Kleisli
import cats.effect._
import com.comcast.ip4s.IpLiteralSyntax
import natchez.Trace.Implicits.noop
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.server.Router
import org.http4s.{Request, Response}
import skunk.Session

object ApplicationCustomer extends IOApp {
  private val session: Resource[IO, Session[IO]] =
    Session.single(
      host = "localhost",
      user = "postgres",
      database = "saloespoc",
      password = Some("my-password")
    )
  private val customerRepository: CustomerRepository[IO] = new CustomerRepositoryImpl(session)

  val httpRoutes: Kleisli[IO, Request[IO], Response[IO]] = Router[IO](
    "/v1/api/" -> CustomerRoutes.routes(customerRepository)
  ).orNotFound

  override def run(args: List[String]): IO[ExitCode] = {
    EmberServerBuilder
      .default[IO]
      .withHost(ipv4"127.0.0.1")
      .withPort(port"8080")
      .withHttpApp(httpRoutes)
      .build
      .use(_ => IO.never)
      .as(ExitCode.Success)
  }

}

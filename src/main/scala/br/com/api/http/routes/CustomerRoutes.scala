package br.com.api.http.routes

import br.com.api.http.routes.validators.Validators.UUIDValid
import br.com.api.model.Customer
import br.com.api.repository.CustomerRepository
import cats.effect.IO
import io.circe.generic.codec.DerivedAsObjectCodec.deriveCodec
import io.circe.syntax.EncoderOps
import org.http4s.HttpRoutes
import org.http4s.circe.CirceEntityCodec.{circeEntityDecoder, circeEntityEncoder}
import org.http4s.circe._
import org.http4s.dsl.Http4sDsl

object CustomerRoutes {

  def routes(customerRepository: CustomerRepository): HttpRoutes[IO] = {

    val dsl = new Http4sDsl[IO] {}
    import dsl._

    HttpRoutes.of[IO] {
      case _@GET -> Root / "customers" / UUIDValid(id) =>
        customerRepository.findCustomer(id).flatMap {
          case Some(customer) => Ok(customer.asJson)
          case None => NotFound(s"Not found customer with id: $id")
        }
      case req@POST -> Root / "customers" =>
        for {
          customer <- req.as[Customer]
          response <- customerRepository.findCustomer(customer.id).flatMap {
            case Some(_) => UnprocessableEntity(s"Customer with id: ${customer.id} already exists")
            case None => Ok(customerRepository.saveCustomer(customer))
          }
        } yield response
    }
  }

}

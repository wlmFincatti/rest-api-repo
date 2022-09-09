package br.com.api.repository

import br.com.api.model.Customer
import br.com.api.repository.sql.CustomerSql._
import cats.effect
import cats.effect.IO
import skunk.Session

import java.util.UUID

trait CustomerRepository {
  def findCustomer(id: UUID): IO[Option[Customer]]

  def saveCustomer(customer: Customer): IO[UUID]
}

object CustomerRepository {
  class CustomerRepositoryImpl(session: effect.Resource[IO, Session[IO]]) extends CustomerRepository {

    override def findCustomer(id: UUID): IO[Option[Customer]] = {
      session.use(s => {
        s.prepare(findCustomerById).use {
          q => q.option(id)
        }
      })
    }

    override def saveCustomer(customer: Customer): IO[UUID] = {
      session.use { session =>
        session.prepare(saveCustomerRepo).use { cmd =>
          cmd.execute(Customer(customer.id, customer.name, customer.document))
            .as(customer.id)
        }
      }
    }
  }
}




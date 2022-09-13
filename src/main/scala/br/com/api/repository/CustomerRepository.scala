package br.com.api.repository

import br.com.api.model.Customer
import br.com.api.repository.sql.CustomerSql._
import cats.effect
import cats.effect.MonadCancelThrow
import cats.implicits.toFunctorOps
import skunk.Session

import java.util.UUID

trait CustomerRepository[F[_]] {
  def findCustomer(id: UUID): F[Option[Customer]]

  def saveCustomer(customer: Customer): F[UUID]
}

object CustomerRepository {
  class CustomerRepositoryImpl[F[_] : MonadCancelThrow](session: effect.Resource[F, Session[F]]) extends CustomerRepository[F] {

    override def findCustomer(id: UUID): F[Option[Customer]] = {
      session.use(s => {
        s.prepare(findCustomerById).use {
          q => q.option(id)
        }
      })
    }

    override def saveCustomer(customer: Customer): F[UUID] = {
      session.use { session =>
        session.prepare(saveCustomerRepo).use { cmd =>
          cmd.execute(customer).as(customer.id)
        }
      }
    }
  }
}




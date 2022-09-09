package br.com.api.http.routes.validators

import cats.implicits._

import java.util.UUID

object Validators {
  protected class UUIDValid(s: String) {
    def unapply(s: String): Option[UUID] = {
      Either.catchNonFatal(UUID.fromString(s)).toOption
    }
  }

  object UUIDValid extends UUIDValid(UUID.randomUUID().toString)
}
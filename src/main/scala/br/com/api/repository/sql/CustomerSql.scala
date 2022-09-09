package br.com.api.repository.sql

import br.com.api.model.Customer
import skunk._
import skunk.codec.all._
import skunk.implicits.toStringOps

import java.util.UUID

object CustomerSql {

  private val customerDecoder: Decoder[Customer] = (uuid ~ varchar ~ varchar).gmap[Customer]

  private val customerDecoderV2: Decoder[Customer] =
    (uuid ~ varchar ~ varchar).map { case ((uuid, name), document) => Customer(uuid, name, document) }

  val findCustomerById: Query[UUID, Customer] =
    sql"select uuid, name, document from customers where uuid = $uuid"
      .query(customerDecoder)

  val findCustomerByIdV2: Query[UUID, Customer] =
    sql"select uuid, name, document from customers where uuid = $uuid"
      .query(customerDecoderV2)

  val findCustomerByIdV3: Query[UUID, Customer] =
    sql"select uuid, name, document from customers where uuid = $uuid"
      .query(uuid ~ varchar ~ varchar)
      .map {
        case ((uuid, name), document) => Customer(uuid, name, document)
        case _ => Customer(UUID.randomUUID(), "", "")
      }

  val saveCustomerRepo: Command[Customer] =
    sql"INSERT INTO customers VALUES ($uuid, $varchar, $varchar)"
      .command
      .gcontramap[Customer]
}

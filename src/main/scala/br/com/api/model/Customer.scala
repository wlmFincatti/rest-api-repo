package br.com.api.model

case class Customer(documentNumber: String,
                    documentType: String,
                    fullName: String,
                    nickName: String,
                    email: String,
                    birthDate: String,
                    phoneNumber: String,
                    phoneType: String,
                    zipCode: String,
                    city: String,
                    district: String,
                    addressStreetName: String,
                    addressNumber: String,
                    complement: String
                   )

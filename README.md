## Poc scala

## Tecnologias
- [Sbt](https://www.scala-sbt.org/)
- [Scala](https://www.scala-lang.org/)
- [Docker](https://www.docker.com/)
- [Docker-compose](https://docs.docker.com/compose/)
- [Localstack](https://github.com/localstack/localstack/)
- [AWS Cli](https://aws.amazon.com/pt/cli/),

## Pré-requisitos
- JDK 11
- Docker
- Docker Compose
- Aws cli

### Docker:

Start the container with Aws services in local machine with the command below. If you want detached mode execute script
with -d.

```sh
docker-compose -f localstack.yml up
```

### Dynamodb:

```sh
aws --endpoint-url http://localhost:8000 dynamodb create-table \
    --table-name Customer \
    --attribute-definitions \
        AttributeName=documentNumber,AttributeType=S \
        AttributeName=email,AttributeType=S \
    --key-schema \
        AttributeName=documentNumber,KeyType=HASH \
        AttributeName=email,KeyType=RANGE \
    --provisioned-throughput \
        ReadCapacityUnits=5,WriteCapacityUnits=5
```

```sh
aws --endpoint-url http://localhost:8000 dynamodb describe-table --table-name Customer
```

```sh
aws --endpoint-url http://localhost:8000 dynamodb put-item \
--table-name Customer \
--item file://src/main/resources/db/nosql/put-item.json \
--return-consumed-capacity TOTAL \
--return-item-collection-metrics SIZE
```

```sh
aws --endpoint-url http://localhost:8000 dynamodb query --table-name Customer \
--key-condition-expression "documentNumber = :documentNumber AND email = :email" \
--expression-attribute-values file://src/main/resources/db/nosql/query-customer.json
```

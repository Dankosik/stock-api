# Getting Started

### Reference Documentation

For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.7.0/gradle-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.7.0/gradle-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.7.0/reference/htmlsingle/#web)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.7.0/reference/htmlsingle/#using.devtools)
* [Apache Kafka Streams Support](https://docs.spring.io/spring-kafka/docs/current/reference/html/#streams-kafka-streams)
* [Apache Kafka Streams Binding Capabilities of Spring Cloud Stream](https://docs.spring.io/spring-cloud-stream/docs/current/reference/htmlsingle/#_kafka_streams_binding_capabilities_of_spring_cloud_stream)
* [Spring for Apache Kafka](https://docs.spring.io/spring-boot/docs/2.7.0/reference/htmlsingle/#messaging.kafka)

### Guides

The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Samples for using Apache Kafka Streams with Spring Cloud stream](https://github.com/spring-cloud/spring-cloud-stream-samples/tree/master/kafka-streams-samples)

### Additional Links

These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

## stock-api

### 2) Изменить контракт

endpoint`ы

* stock-api/moex/stocks/price
* stock-api/moex/stocks/price{ticker}

Возвращают json

```js
"ticker": "VIPS",
"moneyValue": {
    "integer": 10,
    "fractional": 640000000,
    "currency": "usd"
    }
```

Необходимо изменить moneyValue чтобы

```js
"ticker": "VIPS",
"moneyValue": {
    "value": 1064,
    "minorUnits": 100,
    "currency": "usd"
    }
```

## moex-stock-api

### 1) Изменить контракт

endpoint`ы

* moex-stock-api/stocks/price
* moex-stock-api/stocks/price{ticker}

Возвращают json

```js
"ticker": "SBER",
"moneyValue": {
    "integer": 118,
    "fractional": "0.07",
    "currency": "RUR"
}
```

Необходимо изменить moneyValue чтобы

```js
"ticker": "SBER",
"moneyValue": {
    "value": 11807,
    "minorUnits": 100,
    "currency": "RUR"
    }
```

Также подумать как быть с 
```js
"ticker": "VTB",
"moneyValue": {
   "integer": 0,
   "fractional": "0.07123213123",
   "currency": "RUR"
    }
```
И првиести  к  виду как в примерах выше

Пример ответа московской биржи

```js
{
    "marketdata": {
        "columns": [
            "SECID",
            "LAST",
            "TIME"
        ],
            "data": [
            [
                "SBER",
                118.07,
                "18:49:39"
            ]
        ]
    }
}
```

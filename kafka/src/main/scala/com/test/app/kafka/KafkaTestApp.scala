package com.test.app.kafka

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class KafkaTestApp

object KafkaTestApp extends App{
  SpringApplication.run(classOf[KafkaTestApp])
}

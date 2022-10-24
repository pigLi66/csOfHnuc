package com.test.app.mongoDb

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class MongoDBTestApp

object MongoDBTestApp extends App {

  val springApplication = new SpringApplication(classOf[MongoDBTestApp])
  springApplication.run(args: _*)
}

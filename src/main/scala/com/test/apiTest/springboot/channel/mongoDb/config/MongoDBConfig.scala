package com.test.apiTest.springboot.channel.mongoDb.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.data.mongodb.core.MongoTemplate

//@Configurable
class MongoDBConfig extends ApplicationListener[ContextRefreshedEvent]{

  @Autowired var oneMongoTemplate: MongoTemplate = _

  private val TYPEKEY = "_class"

  override def onApplicationEvent(event: ContextRefreshedEvent): Unit = {

  }
}

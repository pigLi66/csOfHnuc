package com.test.apiTest.springboot.channel.mongoDb.mapper

import com.test.apiTest.springboot.channel.mongoDb.model.ApiLog
import org.springframework.data.mongodb.repository.MongoRepository

trait ApiLogMapper extends MongoRepository[ApiLog, String]{

}

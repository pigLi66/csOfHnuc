package com.test.app.mongoDb.mapper

import com.test.app.mongoDb.model.ApiLog
import org.springframework.data.mongodb.repository.MongoRepository

trait ApiLogMapper extends MongoRepository[ApiLog, String]{

}

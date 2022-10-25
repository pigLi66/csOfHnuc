package com.test.app.mongodb.mapper

import com.test.app.mongodb.model.ApiLog
import org.springframework.data.mongodb.repository.MongoRepository

trait ApiLogMapper extends MongoRepository[ApiLog, String]{

}

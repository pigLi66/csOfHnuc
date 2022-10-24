package com.test.app.mongoDb.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

import java.time.LocalDateTime

@Document("apiLog")
class ApiLog {
  @Id
  var id:String = _
  var createdatetime:LocalDateTime = _
  var ip:String = _
  var path:String = _
  var requestParam:String = _
  var result:String = _
  var systemKey:String = _
}

package com.test.apiTest.springboot.channel.mongoDb

import com.test.apiTest.springboot.channel.mongoDb.mapper.ApiLogMapper
import com.test.apiTest.springboot.channel.mongoDb.model.ApiLog
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation
import org.springframework.data.mongodb.core.query.{Criteria, Query, Update}
import org.springframework.stereotype.Component

//@Component
class MongoDBService {

  @Autowired var mongoTemplate: MongoTemplate = _
  @Autowired var apiLogMapper:ApiLogMapper = _
//
//  def save(entity: ApiLog): Unit = {
//    mongoTemplate.save(entity)
//  }
//
//  def update(entity: ApiLog): Unit = {
//    mongoTemplate.updateFirst(
//      new Query(Criteria.where("id").is(entity.id)),
//      new Update().set("ip", entity.ip),
//      classOf[ApiLog]
//    )
//  }
//
//  def remove(id:String): Unit ={
//    mongoTemplate.remove(
//      new Query(Criteria.where("id").is(id)),
//      classOf[ApiLog]
//    )
//  }

  def test(): Unit ={
    apiLogMapper.save(new ApiLog)

    val aggregation = Aggregation.newAggregation(
      Aggregation.project(classOf[ApiLog]),
      Aggregation.lookup("apiLog",
        "id",
        "id",
        "apiLog2")
    )
    mongoTemplate.aggregate(aggregation, "apiLog", classOf[ApiLog])
  }
}

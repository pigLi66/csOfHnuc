package com.test.apiTest.springboot.channel.mongoDb.model

import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.{Document, Field}

import java.time.LocalDateTime

@Document("comment")
class Comment {
  var id:String=_
  var content:String= _
  @Field("publishtime")
  var publishTime:LocalDateTime = _
  @Indexed
  var userid:String =_
  var nickname:String = _
  @Field("createdatetime")
  var createDatetime:LocalDateTime =_
  @Field("likenum")
  var likeNum:Int = _
  @Field("replynum")
  var replyNum:Int = _
  var state:String = _
  @Field("parentid")
  var parentId:String = _
  @Field("articleid")
  var articleId:String =_
}

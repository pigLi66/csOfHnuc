package com.test.app.mongodb.mapper

import com.test.app.mongodb.model.Comment
import org.springframework.data.domain.{Page, Pageable}
import org.springframework.data.mongodb.repository.MongoRepository

trait CommentMapper extends MongoRepository[Comment, String]{

  def findByParentId(parentId:String, pageable:Pageable):Page[Comment]

  def findById(id:String, pageable: Pageable):Page[Comment]

}

package com.test.app.mongodb.service

import com.test.app.mongodb.mapper.CommentMapper
import com.test.app.mongodb.model.Comment
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.{Page, PageRequest}
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.{Criteria, Query, Update}
import org.springframework.stereotype.Service

@Service
class CommentService {

  @Autowired
  var commentMapper: CommentMapper = _
  @Autowired
  var mongoTemplate: MongoTemplate = _

  def pageByParentId(parentId: String, pageNum: Int, pageSize: Int): Page[Comment] = {
    commentMapper.findByParentId(parentId, PageRequest.of(pageNum, pageSize))
  }

  def pageById(id:String, pageNum: Int, pageSize: Int): Page[Comment] = {
    commentMapper.findById(id, PageRequest.of(pageNum, pageSize))
  }

  def like(id:String): Unit ={
    val query = Query.query(Criteria.where("_id").is(id))
    val update = new Update()
    update.inc("likenum")

    mongoTemplate.updateFirst(query, update, classOf[Comment])
  }
}

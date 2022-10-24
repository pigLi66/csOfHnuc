package com.test.app.kafka.config

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.kafka.core.{DefaultKafkaProducerFactory, KafkaAdmin, KafkaTemplate}
import org.springframework.kafka.support.serializer.JsonSerializer

@Configuration
class KafkaConfig {

  private val topicSerializer = new StringSerializer  // 消息的topic类型为String
  private val msgSerializer = new JsonSerializer[Any] // object都以json的形式序列化, 这玩意用jackson的ObjectMapper实现的

  @Bean
  def kafkaAdmin(properties: KafkaProperties): KafkaAdmin = {
    val admin = new KafkaAdmin(properties.buildAdminProperties())
    admin.setFatalIfBrokerNotAvailable(true) // 连不上kafka就无法启动
    admin.setAutoCreate(true) // 初始化时自动创建主题
    properties.getBootstrapServers.add("localhost:9092")
    admin
  }

  @Bean
  def kafkaTemplate(properties: KafkaProperties): KafkaTemplate[String, Any] = {
    val producerFactory = new DefaultKafkaProducerFactory[String, Any](
      properties.buildAdminProperties(),
      topicSerializer,
      msgSerializer
    )
    new KafkaTemplate(producerFactory)
  }

  @Bean
  def producer(properties: KafkaProperties): KafkaProducer[String, Any] =
    new KafkaProducer(properties.buildProducerProperties(), topicSerializer, msgSerializer)



}

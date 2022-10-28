package com.example.app.zookeeper.config

import org.apache.curator.framework.{CuratorFramework, CuratorFrameworkFactory}
import org.apache.curator.retry.RetryNTimes
import org.springframework.context.annotation.{Bean, Configuration}

import javax.annotation.Resource

@Configuration
class ZKConfig {

  @Resource
  var properties: ZKProperties = _

  //  @Bean(initMethod = "start")
  @Bean
  def curatorFramework: CuratorFramework = {
    val curator = CuratorFrameworkFactory.builder()
      .connectString(properties.connectString)
      .sessionTimeoutMs(properties.sessionTimeoutMs)
      .connectionTimeoutMs(properties.connectionTimeoutMs)
      .retryPolicy(new RetryNTimes(properties.retryCount, properties.elapsedTimeMs))
      .build()
    curator.start()
    curator
  }
}
package com.test.app.zookeeper.config

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
    val curator = CuratorFrameworkFactory.newClient(
      properties.connectString,
      properties.sessionTimeoutMs,
      properties.connectionTimeoutMs,
      new RetryNTimes(properties.retryCount, properties.elapsedTimeMs)
    )
    curator.start()
    curator
  }
}

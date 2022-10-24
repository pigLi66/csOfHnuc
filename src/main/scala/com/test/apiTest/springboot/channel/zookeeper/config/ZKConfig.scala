package com.test.apiTest.springboot.channel.zookeeper.config

import org.apache.curator.framework.api.CuratorWatcher
import org.apache.curator.framework.{CuratorFramework, CuratorFrameworkFactory}
import org.apache.curator.retry.RetryNTimes
import org.apache.zookeeper.{AddWatchMode, CreateMode, WatchedEvent}
import org.springframework.context.annotation.{Bean, Configuration}

import javax.annotation.Resource

@Configuration
class ZKConfig {

  @Resource
  var properties: ZKProperties = _

  @Bean(initMethod = "start")
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

  def main(args: Array[String]): Unit = {
    val framework = curatorFramework
    // 空内容节点
    val zNode = framework.create().forPath("/zk_test")
    // 有内容节点
    framework.create().forPath("/zk_test", "内容数据".getBytes())
    // 临时节点
    framework.create()
      .creatingParentsIfNeeded()
      .withMode(CreateMode.EPHEMERAL)
    // 永久监听器
    framework.watchers().add()
      .withMode(AddWatchMode.PERSISTENT_RECURSIVE)
      .usingWatcher(new CuratorWatcher{
        override def process(event: WatchedEvent): Unit = {
          println(event.getType.name())
        }
      })
      .forPath("/zk_test")

  }
}

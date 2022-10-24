package com.test.app.zookeeper.service

import org.apache.curator.framework.CuratorFramework
import org.apache.curator.framework.api.CuratorWatcher
import org.apache.zookeeper.{AddWatchMode, CreateMode, WatchedEvent}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ZKService {

  @Autowired
  var curatorFramework:CuratorFramework = _

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
      .usingWatcher(new CuratorWatcher {
        override def process(event: WatchedEvent): Unit = {
          println(event.getType.name())
        }
      })
      .forPath("/zk_test")

  }
}

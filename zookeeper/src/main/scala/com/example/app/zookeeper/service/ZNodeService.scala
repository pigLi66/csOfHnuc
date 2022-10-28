package com.example.app.zookeeper.service

import org.apache.curator.framework.CuratorFramework
import org.apache.curator.framework.api.CuratorWatcher
import org.apache.zookeeper.Watcher.Event.EventType
import org.apache.zookeeper.{CreateMode, WatchedEvent}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import java.util.concurrent.CountDownLatch

@Service
class ZNodeService {

  @Autowired
  var curatorFramework: CuratorFramework = _

  def syncHandle[T](handleMethod: => T): T = {
    val lockPath = lock()
    try {
      handleMethod
    } finally {
      unlock(lockPath)
    }
  }

  def lock(): String = {
    // 锁路径
    val lock = curatorFramework.create()
      .creatingParentsIfNeeded()
      .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
      .forPath("/locks/seq-")

    // 获取其他的锁
    val child = curatorFramework.getChildren
      .forPath("/locks")

    if (child.size() > 0) { // 如果有其他锁,则等待锁释放
      child.sort(_ compareTo _)
      val index = child.indexOf(lock)

      index match {
        case -1 => throw new RuntimeException("数据异常")
        case _ > 0 => waiting(child.get(index - 1))
      }
    }

    lock
  }

  def waiting(watchPath: String): Unit = {
    val latch = new CountDownLatch(1)

    curatorFramework.watchers().add()
      .usingWatcher(new CuratorWatcher {
        override def process(event: WatchedEvent): Unit = {
          if (event.getType == EventType.NodeDeleted && event.getPath.equals(watchPath)) {
            latch.countDown()
          }
        }
      }).forPath(watchPath)

    latch.await()
  }

  def unlock(lockPath: String): Unit = {
    curatorFramework.delete()
      .forPath(lockPath)
  }

}

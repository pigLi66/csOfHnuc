package com.example.app.zookeeper.service

import org.apache.curator.framework.CuratorFramework
import org.apache.curator.framework.api.{CreateBuilder, CuratorWatcher}
import org.apache.zookeeper.data.Stat
import org.apache.zookeeper.{AddWatchMode, CreateMode, WatchedEvent}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * zookeeper节点相关操作示例
 */
@Service
class ZNodeOperateService {

  @Autowired
  var curatorFramework:CuratorFramework = _

  /**
   * 创建节点示例<br>
   * 创建的节点默认为不带序号的永久节点，如果要更改节点类型
   * 需要使用withModel方法指定。
   */
  def createNode(): Unit ={

    /**
     * 创建一个没有内容的节点
     * {{{
     *   zookeeper> create /empty
     * }}}
     *
     */
    val empty = curatorFramework.create()
      .forPath("/empty")

    /**
     * 创建一个有内容的节点
     * {{{
     *   zookeeper> create /sanguo diaochan
     * }}}
     */
    val diaoChan = curatorFramework.create()
      .forPath("/sanguo", "diaochan".getBytes)

    /**
     * 在sanguo下创建一个子节点shuguo
     * {{{
     *   zookeeper> create /sanguo/shuguo liubei
     * }}}
     */
    val liuBei = curatorFramework.create()
      .forPath("/sanguo/shuguo", "liubei".getBytes)

    /**
     * 创建另外一个子节点
     * {{{
     *   zookeeper> create /sanguo/weiguo caocao
     * }}}
     */
    val weiguo = curatorFramework.create()
      .forPath("/sanguo/weiguo", "caocao".getBytes)

    /**
     * 创建一个带序号的永久节点
     * {{{
     *   zookeeper> create -s /sanguo/weiguo/zhangliao zhangliao
     * }}}
     */
    val zhangliao = curatorFramework.create()
      .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
      .forPath("/sanguo/weiguo/zhangliao", "zhangliao".getBytes)

    /**
     * 创建一个临时节点
     * {{{
     *   zookeeper> create -e /sanguo/wuguo zhouyu
     * }}}
     */
    val zhouyu = curatorFramework.create()
      .withMode(CreateMode.EPHEMERAL)
      .forPath("/sanguo/wuguo", "zhouyu".getBytes)

    /**
     * 创建一个带序号的临时节点
     *  tip: 如果已经存在不带序号的节点，再增加带序号的节点不会报错
     * {{{
     *   zookeeper> create -e -s /sanguo/wuguo zhouyu
     * }}}
     */
    val zhouyu1 = curatorFramework.create()
      .creatingParentsIfNeeded()  // 需要的话递归创建父级
      .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
      .forPath("/sanguo/wuguo", "zhouyu".getBytes())
  }


  /**
   * 获取节点信息
   */
  def findZNodeInfo(): Unit ={

    /**
     * 查询节点的内容
     * {{{
     *   zookeeper> get /sanguo
     * }}}
     */
    val sanguo = curatorFramework.getData
      .forPath("/sanguo")

    /**
     * 查询节点内容以及各种状态
     * {{{
     *   zookeeper> get -s /sanguo
     * }}}
     */
    val stat = new Stat
    val sanguoStat = curatorFramework.getData
      .storingStatIn(stat) // 顺便包括节点的各种状态信息，查出来的状态信息会存到这个对象里
      .forPath("/sanguo")
  }

  /**
   * 设置节点内容
   */
  def updateNode(): Unit ={
    /**
     * 修改节点内容
     * {{{
     *   zookeeper> set /sanguo/weiguo simayi
     * }}}
     */
    val simayi = curatorFramework.setData()
      .forPath("/sanguo/weiguo", "simayi".getBytes)
  }

  def addWatcher(): Unit ={

    /**
     * 添加一个一次性的监听器
     * {{{
     *   zookeeper> ls -w /sanguo
     * }}}
     */
    val watcher = curatorFramework.watchers().add()
      .withMode(AddWatchMode.PERSISTENT) // 一次性监听器器，监听一次就会被移除
      .usingWatcher(new CuratorWatcher { // 监听器的回调方法
        override def process(event: WatchedEvent): Unit = {
          // todo something
        }
      })
      .forPath("/sanguo")
  }


  def deleteNode(): Unit ={
    /**
     * 删除节点
     * 如果还有子节点，将会报错
     * {{{
     *   zookeeper> delete /sanguo
     * }}}
     */
    val delete = curatorFramework.delete()
      .forPath("/sanguo")

    /**
     * 删除节点，顺便删除所有子节点
     * {{{
     *   zookeeper> deleteall /sanguo
     * }}}
     */
    val deleteAll = curatorFramework.delete()
      .deletingChildrenIfNeeded()
      .forPath("/sanguo")

  }

}

### Zookeeper学习笔记
本示例的学习内容来自B站视频：[【尚硅谷】大数据技术之Zookeeper 3.5.7版本教程](https://www.bilibili.com/video/BV1to4y1C7gw)

#### Zookeeper 应用场景
1. 统一命名服务
2. 统一配置管理
3. 统一集群管理
4. 服务器节点动态上下线
5. 软负载均衡

#### Zookeeper 选举机制
  面试题重点！
  Zookeeper采用半数选举原则。
##### 第一次启动
![img.png](img/第一次启动.png)
zookeeper集群在启动时，每个实例会依次注册到集群。
1. 每个实例先给自己投一票
2. 然后每个实例注册到集群后，依次与以注册的实例比较myid，myid大的获得选票
3. 集群中第一个获得半数以上的实例，成为集群的leader，其他的自动变成follower
4. 集群的leader一旦产生，启动过程中其他集群获得再多的选票，也不会把leader让出去了

#### Zookeeper 写原理
依然是半数原则，不过类似mysql的读写分离，follower是没有写权限的，只有leader有
##### 写入leader
![img.png](img/写原理.png)
1. 客户端把数据写入leader
2. leader把数据同步到follower，等待follower返回ack应答
3. 有半数以上的节点写入完毕，也就是一半的follower都ack了，响应客户端写入成功
4. 剩下没写入的follower继续进行写入

##### 写入follower
![img.png](img/写入follower.png)
1. 客户端发送写请求到follower，follower没有权限，于是转发给了leader
2. leader收到转发请求，开始让follower写入
3. 半数follower写入完成，应答ack后，给转发写请求的follower返回ack
4. 接收到ack的follower响应客户端写入成功
5. 剩下没写入的follower继续进行写入
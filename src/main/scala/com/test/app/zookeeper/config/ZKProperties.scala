package com.test.app.zookeeper.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "curator")
class ZKProperties {
  var retryCount:Int = _
  var elapsedTimeMs:Int = _
  var connectString:String = _
  var sessionTimeoutMs:Int = _
  var connectionTimeoutMs:Int = _
}

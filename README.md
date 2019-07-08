##### 使用
* 下载
   * TODO
* 本地编译
```
mvn clean package -Dmaven.test.skip=true
在 hound-collect 目录下面生成对应的jar文件
项目启动后面添加参数 -javaagent:xxx\hound-agent.jar
config 配置目前用处不大

```
* 本地测试预览
```$xslt
如下，目前使用的三个测试插件数据
{"bizBaggage":{},"endTime":1562249883956,"operationName":"plugin-springBean","parentId":"0.1.1","sampled":true,"spanId":"0.1.1.1","startTime":1562249883951,"sysBaggage":{"method":"servce1","class":"com.example.demo.service.Service"},"traceId":"c0a89a011562249883949100217996"}  5 ms
{"bizBaggage":{},"endTime":1562249883956,"operationName":"plugin-springWebmvc","parentId":"0.1","sampled":true,"spanId":"0.1.1","startTime":1562249883951,"sysBaggage":{"method":"test1","class":"com.example.demo.TestController"},"traceId":"c0a89a011562249883949100217996"}  5 ms
{"bizBaggage":{},"endTime":1562249883960,"operationName":"plugin-tomcat","parentId":"0","sampled":true,"spanId":"0.1","startTime":1562249883949,"sysBaggage":{"method":"invoke","class":"org.apache.catalina.core.StandardHostValve"},"traceId":"c0a89a011562249883949100217996"}  11 ms

```
##### 系统设计
![jgt](https://github.com/bytes1024/files/blob/master/hound/images/jgt.png)

##### 配置参数
`-javaagent:agent.jar=agent.properties`

key|value|支持|描述
|---|---|---|---|
|bytes.hound.agent.id|xxx|Y|id标识，后期接入ID校验使用
|bytes.hound.agent.secret|xx|T|秘钥
|bytes.hound.transfer.type|web|T-|支持的传输类型
|bytes.hound.transfer.web.address|http://127.0.0.1:9999/v1/transfer/receive|T|web传输服务地址
|bytes.hound.transfer.web.batch.max|<0 或 >0|Y|批量传输<=0实时传输
|bytes.hound.transfer.web.batch.size|1M|Y|堆积数据大小

##### 功能
##### [任务列表](https://github.com/bytes1024/hound/wiki/%E5%BC%80%E5%8F%91%E4%BB%BB%E5%8A%A1%E5%88%97%E8%A1%A8)
##### 支持插件
名称|版本|粒度
|---|---|---|
|tomcat|6+|粗|
|webflux|5.0.0.release|粗|
|okHttp|3.7.0|粗
|spring|5.0.0.RELEASE|粗
|httpClient|4.5.2|粗
|dubbo|TODO|
|gson|2.8.0|粗
|fastjson|1.2.49|粗
|jackson|TODO
|hystrix|TODO
|mybatis|TODO
|hibernate(jpa)|TODO
|mysql-jdbc|TODO
|redis-lettuce|TODO
|spring-data-*(jpa,redis,es,....)|TODO
|rabbitmq|TODO
|rocketmq|TODO
|mongodb|TODO
|log4j|TODO
|dbcp|TODO
|druid|1.1.10|粗
|hikaricp|TODO
|TODO
##### [文档](https://github.com/bytes1024/hound/wiki)

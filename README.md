##### hound的模块
* 要进行hound的开发需要首先了解hound的模块定义，及每个模块的基础能力
* hound 目前定义了如下工程模块
  * hound-bom
    * 系统pom依赖管理(但并不完全管理，插件部分由插件本身自定义)                    
  * hound-collect
    * 数据采集器(agent , monitor)等
  * hound-commons
    * 公共包utils等等
  * hound-loader
    * 插件加载器（目前只支持spi方式加载）
  * hound-plugins
    * 插件模块（含定义，实现等）
    * hound-plugins-define
      * 插件的定义（插件的实现需要引入该模块，hound-loader也会加载该模块实现）
    * hound-plugins-bom
    * hound-plugins-tomcat
    * hound-plugins-webflux
    * hound-plugins-xxxx...
      * 其他实现
  * hound-server
    * 服务端（collect收集到数据以后通过tranfer传输到服务端）
  * hound-transfers
    * 数据传输模块（含定义，实现等）
    * hound-transfers-bom
    * hound-transfers-define
      * 传输器的定义（传输插件的实现需要引入该木块，hound-loader加载该模块实现）
    * hound-transfers-web
    * hound-transfers-xxxx...
#### 数据追踪
* 目前使用开源实现[sofaTracer](https://github.com/sofastack/sofa-tracer)


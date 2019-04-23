# 行人重识别Java后端

负责与数据库的大部分交互，与Tomcat文件服务器的交互，并将所有数据提供给前端。

## Tomcat文件服务器
- 使用Tomcat[虚拟目录功能](https://blog.cweihang.io/java/java-web/note#tomcat-xu-ni-mu-lu)
- 当前虚拟目录物理地址为：222.201.145.237:/home/cwh/coding/reid_file_backend
- 当前虚拟目录web地址为：http://222.201.145.237:8080/reid

## MongoDB配置
- 初始化：见 [Mini Deployment](doc/deploy.md)
- 配置：建立MongoDB user和DB后，填写数据库对应的信息到[application-dev.yml](src/main/resources/application-dev.yml)
- 文档（即关系数据库中的表）：需要在MongoDB建立的每个文档都在[domain](src/main/java/scut/cwh/reid/domain)这个包下有对应的类，
这些类也可以认为是一个个JavaBean
- 数据库中间层：在[repository](src/main/java/scut/cwh/reid/repository)这个package下：
  - repo为各个文档对应的Spring boot MongoRepository接口，会自动被Spring Boot实例化进行MongoDB的访问，只有在这里声明的接口才会在MongoDB中自动建表
  - ctrl为各个MongoRepository接口的具体使用，是一种纵向上的切分和解耦，推荐将MongoRepository的直接操作都放在这里，与[业务逻辑](src/main/java/scut/cwh/reid/api)分开

## Machine Reid功能
- 由[Django Python服务端](https://git.so-link.org/ahangchen/deep-reid-backend)实现，通过Web API进行交互，请参照该项目说明进行配置

## 编译运行
```shell
mongod # 或者 sudo service mongod start
mvn package # 打包
java -jar target/reid-0.0.1-SNAPSHOT.jar # 会自动启动一个Tomcat容器在8080端口，如果已经被占用，会自动顺延到8081
```

## 代码结构
```shell
├── doc：文档
├── src
│   └── main
│       ├── java
│       │   ├── framework：框架相关封装代码，让HttpResponse相关编码更简单，平时无需修改
│       │   └── scut
│       │       └── cwh
│       │           └── reid
│       │               ├── algo # 原本打算用来放跟Django端交互的代码，但因为代码过于简单，目前这部分逻辑直接放在api package里
│       │               ├── api # Spring boot Controller,用于接收请求，解析参数，调用repository查询数据库
│       │               ├── config # 与application-dev.yml配合使用，动态读取配置文件，可以利用这个避免一些hardcode，如Django服务器地址
│       │               ├── domain # 一系列JavaBean
│       │               ├── ReidApplication.java Spring boot的入口类，一般不需要改代码
│       │               ├── repository： 与数据库交互
│       │               └── utils: Java工具类，业务无关，可跨项目复用，业务相关代码不要放在这里
│       └── resources
│           ├── application-dev.yml # debug时用的配置，当前用的配置是这个
│           ├── application-prod.yml # 生产环境用的配置
│           └── application.yml # 在active中选择用dev还是prod
└── target
    └── classes  

```

## 一个请求的处理过程
- 前端发起请求
- api包中的Controller接收到请求，解析参数
- 调用repository中的接口，读取MongoDB数据，假如MongoDB的文档没有创建，会根据domain包中的类创建对应的文档
- 调用Django API接口，读取排序数据（可选）
- 用utils中的ResultUtil将返回内容封装为包含code, msg, data三个固定字段的Result对象
- 由api包中的Controller将Result对象转为ResponseBody，输出到前端

## 当前部署
- CPU内存服务器：浪潮服务器上的虚拟机spring，ip为222.201.145.237，可以用vsphere client登录222.201.145.169进行虚拟机管理
- GPU服务器：执行Django API中的增量训练功能，通过ssh公钥登录方式与CPU服务器进行模型传输
- Java后端项目地址：cwh@222.201.145.237:/home/cwh/coding/Reid-backend，运行在8081端口
- Python后端项目（CPU功能）地址：cwh@222.201.145.237:/home/cwh/coding/deep_reid_backend，运行在8082端口
- Python后端项目（GPU功能）地址：cwh@222.201.145.200:/home/cwh/coding/deep_reid_backend，运行在8082端口

## 详细Web接口文档
见[doc/api](doc/api.md)
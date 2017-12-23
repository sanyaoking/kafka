# kafka
kafka+bridge

vertx学习之kafka整合demo,项目中的resources中上传了kafka与zookeeper安装包与解压文件。

参照kafka官网，启动服务前，要注意修改kafka配置文件中的zookeeper.properties中的dataDir的路径 
然后启动代码目录下静态文件中的kafka服务(以下为对应的命令):
1. bin/zookeeper-server-start.sh config/zookeeper.properties 
2. bin/kafka-server-start.sh config/server.properties 
完成上面步骤后，Testkafka类中的测试代码 本代码测试的时候应注意多次运行代码后，每次都会比上一次多打印5条信息，这是因为kafka默认保存两个星期的消息，所以每次重新运行的时候，生产着会新增五条信息，但是消费者消费的确实队列中全部的信息

vertx桥接只使用了官方提供的jar,此jar基于NetServer服务进行桥接的，所以客户端需要NetClient进行消息通讯。在使用webClient时，NetService服务没有接收到请求，所以如果需要WebClient来进行通讯的话需要自行开发工具包来实现。

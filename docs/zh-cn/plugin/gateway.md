# hserver-plugin-gateway
该插件提供tcp和http两种级别的数据拦截转发功能，让网关支持其他协议或者http协议

# BusinessTcp BusinessHttp7 BusinessHttp4
我们提供了这三个类，来进行代理的数据传输
tcp就是最原始的数据包 http7模式不支持ws http4支持ws

我们通过继承类重写对应的方法即可

```java
    /**
    * 数据入场
    * 业务处理模式，拦截器 限流等
    *
    * @param t
    * @return
    */
    Object in(ChannelHandlerContext ctx,T t);


    /**
     * 代理host
     * 业务代码只处理选择什么样的服务，比如常见随机模式 hash模式 循环模式等
     *
     * @param t
     * @param sourceSocketAddress
     * @return
     */
    SocketAddress getProxyHost(ChannelHandlerContext ctx,T t,SocketAddress sourceSocketAddress);


    /**
     * 数据出场
     * 数据加密等操作
     *
     * @param u
     * @return
     */
    Object out(Channel channel, U u);
```
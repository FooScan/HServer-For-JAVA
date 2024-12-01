package cn.hserver.plugin.gateway.handler.http7;

import cn.hserver.core.server.util.ReleaseUtil;
import cn.hserver.plugin.gateway.business.Business;
import cn.hserver.plugin.gateway.business.BusinessHttp7;
import cn.hserver.plugin.gateway.handler.OutBaseChannelInboundHandlerAdapter;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpObject;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Http7BackendHandler extends OutBaseChannelInboundHandlerAdapter {
    private static final Logger log = LoggerFactory.getLogger(Http7BackendHandler.class);

    public Http7BackendHandler(Channel inboundChannel, Business business) {
        super(inboundChannel, business);
    }
}

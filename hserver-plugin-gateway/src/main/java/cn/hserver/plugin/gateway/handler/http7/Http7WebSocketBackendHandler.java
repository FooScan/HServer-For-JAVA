package cn.hserver.plugin.gateway.handler.http7;

import cn.hserver.plugin.gateway.business.BusinessHttp7;
import io.netty.channel.*;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketHandshakeException;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Http7WebSocketBackendHandler extends ChannelInboundHandlerAdapter{
    private static final Logger log = LoggerFactory.getLogger(Http7WebSocketBackendHandler.class);

    private final WebSocketClientHandshaker handshakes;
    private ChannelPromise handshakeFuture;
    private final Channel inboundChannel;

    private final BusinessHttp7 businessHttp7;

    public Http7WebSocketBackendHandler(WebSocketClientHandshaker handshaker, Channel inboundChannel, BusinessHttp7 businessHttp7) {
        this.handshakes = handshaker;
        this.inboundChannel = inboundChannel;
        this.businessHttp7 = businessHttp7;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        handshakeFuture = ctx.newPromise();
    }

    public ChannelFuture handshakeFuture() {
        return handshakeFuture;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        handshakes.handshake(ctx.channel());
    }

    @Override
    public void channelRead(final ChannelHandlerContext ctx, Object msg) {
        Channel ch = ctx.channel();
        if (!handshakes.isHandshakeComplete()) {
            try {
                handshakes.finishHandshake(ch, (FullHttpResponse) msg);
                log.info("websocket Handshake 完成!");
                handshakeFuture.setSuccess();
            } catch (WebSocketHandshakeException e) {
                log.info("websocket连接失败!");
                handshakeFuture.setFailure(e);
            }
            ReferenceCountUtil.release(msg);
            return;
        }
        if (msg instanceof WebSocketFrame) {
            try {
                Object out = businessHttp7.out(inboundChannel, msg);
                if (out == null) {
                    return;
                }
                inboundChannel.writeAndFlush(out);
            }catch (Throwable e){
                log.error(e.getMessage(),e);
            }
        } else {
            ctx.channel().close();
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        Http7FrontendHandler.closeOnFlush(inboundChannel);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        if (!handshakeFuture.isDone()) {
            handshakeFuture.setFailure(cause);
        }
        Http7FrontendHandler.closeOnFlush(ctx.channel());
    }
}

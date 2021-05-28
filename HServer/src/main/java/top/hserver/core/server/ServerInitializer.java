package top.hserver.core.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.ReferenceCountUtil;
import top.hserver.core.interfaces.ProtocolDispatcherAdapter;
import top.hserver.core.ioc.IocUtil;
import top.hserver.core.server.util.ByteBufUtil;

import java.util.List;

/**
 * @author hxm
 */
public class ServerInitializer extends ChannelInitializer<Channel> {

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new ProtocolDispatcher());
    }


    public static class ProtocolDispatcher extends ByteToMessageDecoder {
        @Override
        public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
            if (in.readableBytes() < 5) {
                return;
            }
            /**
             * copy 最多512个字节作为消息头数据判断
             */
            ByteBuf copy = in.copy(0, Math.min(in.readableBytes(), 512));
            try {
                byte[] bytes = ByteBufUtil.byteBufToBytes(copy);
                List<ProtocolDispatcherAdapter> listBean = IocUtil.getListBean(ProtocolDispatcherAdapter.class);
                for (ProtocolDispatcherAdapter protocolDispatcherAdapter : listBean) {
                    if (protocolDispatcherAdapter.dispatcher(ctx, ctx.pipeline(), bytes, this)) {
                        return;
                    }
                }
            } finally {
                ReferenceCountUtil.release(copy);
            }
        }
    }

}

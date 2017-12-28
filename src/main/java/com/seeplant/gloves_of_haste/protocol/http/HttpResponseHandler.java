package com.seeplant.gloves_of_haste.protocol.http;

import com.seeplant.gloves_of_haste.JobCounter;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpContent;

/**
 * 应答处理的Handler
 * 
 * @author yuantao
 *
 */
public class HttpResponseHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpContent) {
            HttpContent content = (HttpContent)msg;
            ByteBuf buf = content.content();
            System.out.println(buf.toString(io.netty.util.CharsetUtil.UTF_8));
            buf.release();
            JobCounter.decrementAndGet();
        }
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
        throws Exception {
        System.out.println(System.currentTimeMillis() + " -- " + cause.getMessage() + " ch " + ctx.channel());
    }
}

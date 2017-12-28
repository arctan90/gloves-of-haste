package com.seeplant.gloves_of_haste.protocol.http;

import com.seeplant.gloves_of_haste.core.HandlerConfiguratorInterface;
import io.netty.channel.Channel;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;

/**
 * 提供HTTP编解码器的channel配置
 * @author yuantao
 *
 */
public class HttpConfigurator implements HandlerConfiguratorInterface {
    private static final int HTTP_AGGREGATE_SIZE = 8192;

    @Override
    public void configChannel(Channel ch) {
        SocketChannel channel = (SocketChannel)ch;
        channel.config().setKeepAlive(true);
        channel.config().setTcpNoDelay(true);
        channel.pipeline()
            .addLast(new HttpClientCodec())
            .addLast(new HttpObjectAggregator(HTTP_AGGREGATE_SIZE))
            .addLast(new HttpResponseHandler());
    }
}

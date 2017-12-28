package com.seeplant.gloves_of_haste.protocol.http;

import com.seeplant.gloves_of_haste.core.BaseChannelPoolHandler;
import com.seeplant.gloves_of_haste.core.HandlerConfiguratorInterface;
import io.netty.channel.Channel;

public class HttpChannelPoolHandler extends BaseChannelPoolHandler {

    public HttpChannelPoolHandler(HandlerConfiguratorInterface configurator) {
        super(configurator);
    }

    @Override
    public void channelReleased(Channel ch) throws Exception {
        System.out.println("channel release " + ch);
    }

}

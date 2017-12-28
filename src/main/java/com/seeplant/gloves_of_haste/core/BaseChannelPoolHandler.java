package com.seeplant.gloves_of_haste.core;

import io.netty.channel.Channel;
import io.netty.channel.pool.ChannelPoolHandler;

/**
 * 这个类是为线程池中channel相关的操作提供处理回调
 * 这是抽象基类，提供基本的处理能力
 * @author yuantao
 *
 */
public abstract class BaseChannelPoolHandler implements ChannelPoolHandler {

    private HandlerConfiguratorInterface configurator;
    public BaseChannelPoolHandler(HandlerConfiguratorInterface configurator) {
        this.configurator = configurator;
    }
    /**
     * 因为是裸的channel，所以需要给他配置上编解码器
     * 只需要配置一次就可以，因为channel会被还回到池里
     */
    @Override
    public void channelCreated(Channel ch) throws Exception {
        configurator.configChannel(ch);
    }

    @Override
    public void channelReleased(Channel ch) throws Exception {}

    @Override
    public void channelAcquired(Channel ch) throws Exception {}
}

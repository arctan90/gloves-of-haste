package com.seeplant.gloves_of_haste.core;


import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.pool.AbstractChannelPoolMap;
import io.netty.channel.pool.ChannelPoolHandler;
import io.netty.channel.pool.FixedChannelPool;
import io.netty.channel.pool.SimpleChannelPool;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;

/**
 * 客户端连接池
 * 提供与业务层协议无关的连接池功能
 * @author yuantao
 *
 */
public class TcpClientPool {
    final EventLoopGroup group = new NioEventLoopGroup();
    final Bootstrap bootstrap = new Bootstrap();
    private static final int thread_num = Runtime.getRuntime().availableProcessors();
    // key 是地址， value是pool，即一个地址一个pool
    private AbstractChannelPoolMap<InetSocketAddress, SimpleChannelPool> poolMap;

    public void build(ChannelPoolHandler poolHandler) throws Exception {
        bootstrap.group(group)
            .channel(NioSocketChannel.class)
            .option(ChannelOption.TCP_NODELAY, true)
            .option(ChannelOption.SO_KEEPALIVE, true);

        poolMap = new AbstractChannelPoolMap<InetSocketAddress, SimpleChannelPool>() {
            @Override
            protected SimpleChannelPool newPool(InetSocketAddress key) {
                return new FixedChannelPool(bootstrap.remoteAddress(key), poolHandler, thread_num);
            }
        };
    }

    public SimpleChannelPool getPool(InetSocketAddress address) {
        return poolMap.get(address);
    }

    public void close() {
        poolMap.close();
        group.shutdownGracefully();
    }

    /**
     * 高度封装的异步写数据
     * 每个InetSocketAddress地址对应一个连接池，
     * 
     * @param address
     * @param message
     */
    public void asyncWriteMessage(InetSocketAddress address, Object message) {
        SimpleChannelPool pool = getPool(address);
        Future<Channel> future = pool.acquire();
        // 获取到实例后发消息
        future.addListener((FutureListener<Channel>)f -> {
            if (f.isSuccess()) {
                Channel ch = f.getNow();
                if (ch.isWritable()) {
                    ch.writeAndFlush(message);
                }
                // 归还实例
                pool.release(ch);
            }
        });
    }

    public boolean syncWriteMessage(InetSocketAddress address, Object message) {
        SimpleChannelPool pool = getPool(address);
        Future<Channel> future = pool.acquire();
        try {
            Channel channel = future.get();
            if (channel.isWritable()) {
                channel.writeAndFlush(message);
                pool.release(channel);
                return true;
            }
            pool.release(channel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}

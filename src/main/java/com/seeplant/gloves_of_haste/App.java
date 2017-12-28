package com.seeplant.gloves_of_haste;

import java.net.InetSocketAddress;
import java.net.URI;

import com.seeplant.gloves_of_haste.core.TcpClientPool;
import com.seeplant.gloves_of_haste.protocol.http.HttpChannelPoolHandler;
import com.seeplant.gloves_of_haste.protocol.http.HttpConfigurator;
import com.seeplant.gloves_of_haste.protocol.http.HttpMsgComposer;

/**
 * Hello world!
 * 让你的客户端疯狂发消息，根本停不下来
 * 
 * @author yuantao
 */
public class App {
    public static void main(String[] args) throws Exception {
        TcpClientPool pool = new TcpClientPool();
        pool.build(new HttpChannelPoolHandler(new HttpConfigurator()));

        String url = "http://163.com";
        URI uri = new URI(url);
        String host = uri.getHost();
        int port = uri.getPort() == -1 ? 80 : uri.getPort();
        InetSocketAddress address = new InetSocketAddress(host, port);
        JobCounter.incrementAndGet();

        for (int i = 0; i < 10; i++) {
            pool.asyncWriteMessage(address, HttpMsgComposer.compose(uri, "Hello World"));
        }

        while (true) {
            Thread.sleep(1000L);
            if (JobCounter.get() <= 0) {
                pool.close();
                break;
            }
        }
    }
}

# Gloves-of-Haste 撸得快

This is a Netty based Client for Stress Tesing.

这是一个用Netty实现的客户端连接池。可以把大量的消息体扔给这个连接池，让他疯狂的向服务端发
消息。

目前支持的传输层协议：
TCP
未来支持的传输层协议：
UDP

目前支持的应用层协议：
HTTP
未来支持的应用层协议：
HTTPS WebSocket WSS MQTT CoAP ProtocolBuf

===========================================================================
版本1.0.0  2017/12/28 17:14
1. 支持TCP的客户端
2. 应用层协议支持HTTP

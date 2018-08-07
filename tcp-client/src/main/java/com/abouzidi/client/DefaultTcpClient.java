package com.abouzidi.client;

import org.bouncycastle.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
public class DefaultTcpClient implements TcpClient {
	
	private static String message="hello";

	@Value("${server.ip}")
	private String serverIp;

	@Value("${server.port}")
	private int serverPort;

	@Autowired
	@Qualifier("clientGroup")
	private NioEventLoopGroup clientGroup;

	@Autowired
	private TcpClientInitializer tcpClientInitializer;

	@Override
	public void sendMessage() {
		Bootstrap b = new Bootstrap();
		b.group(clientGroup).channel(NioSocketChannel.class).remoteAddress(serverIp, serverPort)
				.handler(tcpClientInitializer);
		try {
			ChannelFuture channelFuture = b.connect().sync();
			//message = length (1 byte)+text
			channelFuture.channel().writeAndFlush(Arrays.concatenate(new byte[] {(byte) message.length()}, message.getBytes()));
			log.info("message sent...");
		} catch (InterruptedException e) {
			log.error("Error ", e);
		}
	}

}

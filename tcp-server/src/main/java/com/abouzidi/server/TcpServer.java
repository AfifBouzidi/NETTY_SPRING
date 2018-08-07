package com.abouzidi.server;

import java.net.InetSocketAddress;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;

@Component
public class TcpServer {
	
	private ChannelFuture serverChannelFuture;

	@Autowired
	private ServerBootstrap serverBootstrap;
	
	@Autowired
	private InetSocketAddress tcpSocketAddress;
	
	@PostConstruct
	public void start() throws InterruptedException {
		serverChannelFuture = serverBootstrap.bind(tcpSocketAddress).sync();
	}

	@PreDestroy
	public void stop() throws InterruptedException {
		serverChannelFuture.channel().closeFuture();
	}

}

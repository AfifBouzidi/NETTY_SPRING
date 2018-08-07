package com.abouzidi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.netty.channel.nio.NioEventLoopGroup;

@Configuration
public class NettyClientConfiguration {

	@Value("${client.thread.count}")
	private int clientCount;

	@Bean(name = "clientGroup",destroyMethod = "shutdownGracefully")
	public NioEventLoopGroup clientGroup() {
		return new NioEventLoopGroup(clientCount);
	}

}

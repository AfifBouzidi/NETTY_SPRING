package com.abouzidi.config;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.abouzidi.server.TcpServerInitializer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

@Configuration
public class NettyServerConfiguration {

	@Value("${boss.thread.count}")
	private int bossCount;

	@Value("${worker.thread.count}")
	private int workerCount;

	@Value("${tcp.port}")
	private int tcpPort;

	@Value("${so.keepalive}")
	private boolean keepAlive;

	@Value("${so.backlog}")
	private int backlog;

	@Value("${so.connect.timeout}")
	private int connectTimeout;
	
	@Autowired
	private TcpServerInitializer tcpServerInitializer;
	
	@Bean
	public InetSocketAddress tcpPort() {
		return new InetSocketAddress(tcpPort);
	}
	
	@Bean(destroyMethod = "shutdownGracefully")
	public NioEventLoopGroup bossGroup() {
		return new NioEventLoopGroup(bossCount);
	}
	
	@Bean(destroyMethod = "shutdownGracefully")
	public NioEventLoopGroup workerGroup() {
		return new NioEventLoopGroup(workerCount);
	}
	
	@Bean
	public Map<ChannelOption, Object> tcpChannelOptions() {
		Map<ChannelOption, Object> options = new HashMap<ChannelOption, Object>();
		options.put(ChannelOption.SO_KEEPALIVE, keepAlive);
		options.put(ChannelOption.SO_BACKLOG, backlog);
		options.put(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeout);
		return options;
	}
	
	@Bean
	public ServerBootstrap bootstrap() {
		ServerBootstrap b = new ServerBootstrap();
		b.group(bossGroup(), workerGroup()).channel(NioServerSocketChannel.class).childHandler(tcpServerInitializer);
		Map<ChannelOption, Object> tcpChannelOptions = tcpChannelOptions();
		Iterator<Map.Entry<ChannelOption, Object>> entrySetIterator = tcpChannelOptions.entrySet().iterator();
		while (entrySetIterator.hasNext()) {
			ChannelOption option = entrySetIterator.next().getKey();
			b.option(option, tcpChannelOptions.get(option));
		}
		return b;
	}
	
}

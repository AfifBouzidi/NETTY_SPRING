package com.abouzidi.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.abouzidi.handlers.DecoderHandler;
import com.abouzidi.handlers.EncoderHandler;
import com.abouzidi.handlers.TcpHandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

@Component
public class TcpServerInitializer extends  ChannelInitializer<SocketChannel>{

	@Autowired
	private TcpHandler tcpHandler;
	
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		
		ChannelPipeline p = ch.pipeline();
		p.addLast("decoderHandler", new DecoderHandler());
		p.addLast("tcpHandler", tcpHandler);
		p.addLast("encoderHandler", new EncoderHandler());
	}

}

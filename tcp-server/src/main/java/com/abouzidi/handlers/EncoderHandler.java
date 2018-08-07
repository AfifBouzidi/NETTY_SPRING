package com.abouzidi.handlers;

import java.util.List;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

public class EncoderHandler extends MessageToMessageEncoder<byte[]>{

	@Override
	protected void encode(ChannelHandlerContext arg0, byte[] msg, List<Object> out) throws Exception {
		out.add(Unpooled.wrappedBuffer(msg));		
	}

}

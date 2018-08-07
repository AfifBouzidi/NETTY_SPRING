package com.abouzidi.handlers;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.ReplayingDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DecoderHandler extends ReplayingDecoder<HeaderState> {

	private byte length;

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

		switch (state()) {
		case READ_LENGTH:
			length = in.readByte();
		case READ_PAYLOAD:
			ByteBuf frame = in.readBytes(length);
			out.add(readMessage(frame.array()));
			this.reset();
			break;
		default:
			throw new DecoderException("Unknown decoding state: " + state());
		}
	}

	private Object readMessage(byte[] array) {
		return new String(array);
	}

	public DecoderHandler() {
		super(HeaderState.READ_LENGTH);
	}

	private void reset() {
		checkpoint(HeaderState.READ_LENGTH);
	}

}

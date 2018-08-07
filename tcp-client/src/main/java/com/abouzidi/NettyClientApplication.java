package com.abouzidi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.abouzidi.client.TcpClient;

@SpringBootApplication
public class NettyClientApplication implements CommandLineRunner{

	
	@Autowired
	private TcpClient tcpClient; 
	
	public static void main(String[] args) {
		SpringApplication.run(NettyClientApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		tcpClient.sendMessage();
	}

}

package com.deerlili.mp;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;

import javax.swing.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
@MapperScan("com.deerlili.mp.dao")
public class MpApplication extends SpringBootServletInitializer {

	private static Logger logger = LoggerFactory.getLogger(MpApplication.class);

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(MpApplication.class);
	}

	public static void main(String[] args) throws UnknownHostException {
		// SpringApplication.run(MpApplication.class, args);
		ConfigurableEnvironment env = new SpringApplication(MpApplication.class).run(args).getEnvironment();
		String envPort = env.getProperty("server.port");
		String envContext = env.getProperty("server.contextPath");
		String port = envPort == null ? "8080" : envPort;
		String context = envContext == null ? "" : envContext;
		String path = port + "" + context + "/doc.html";
		String externalAPI = InetAddress.getLocalHost().getHostAddress();
		logger.info(
				"Access URLs:\n"
						+ "----------------------------------------------------------\n\t"
						+ "Local-API: \t\thttp://127.0.0.1:{}\n\t"
						+ "External-API: \thttp://{}:{}\n\t"
						+ "web-URL: \t\thttp://127.0.0.1:{}/index.html\n"
						+ "----------------------------------------------------------",
				path, externalAPI, path, port);

	}

}

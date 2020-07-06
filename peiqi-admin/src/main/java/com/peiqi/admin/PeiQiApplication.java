package com.peiqi.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * 程序启动类
 * 
 * @author STILL
 *
 */
@ComponentScan("com.peiqi")
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class PeiQiApplication {
	public static void main(String[] args) {
		System.setProperty("spring.devtools.restart.enabled", "false");
		SpringApplication.run(PeiQiApplication.class, args);
		System.err.println("__________       .__________  .__  \r\n" + 
				"\\______   \\ ____ |__\\_____  \\ |__| \r\n" + 
				" |     ___// __ \\|  |/  / \\  \\|  | \r\n" + 
				" |    |   \\  ___/|  /   \\_/.  \\  | \r\n" + 
				" |____|    \\___  >__\\_____\\ \\_/__| \r\n" + 
				"               \\/          \\__>  启动成功o(∩_∩)o ");
	}
}
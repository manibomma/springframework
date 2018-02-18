package com.learning.springs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:/application-${spring.profiles.active}.properties")
public class AppConfig {

	@Value("${greeting.text}")
	private String greetingText;
	
	@Value("${preamble.text}")
	private String preambleText;
	public class Worker {
		
		private String text1;
		private String text2;
		
		@Value("#{new Boolean(environment['spring.profiles.active'] == 'dev')}")
		private boolean isDev;
		public Worker(String text1, String text2) {
			this.text1 = text1;
			this.text2 = text2;
		}
		
		public void execute() {
			System.out.println(text1 + " " + text2 + ", isDev : " + isDev);
		}
	}
	
	@Bean
	public Worker worker() {
		return new Worker(preambleText, greetingText);
	}
	
	public static void main(String []args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		Worker worker = context.getBean(Worker.class);
		worker.execute();
	}
}

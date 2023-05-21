package com.xuananh.accountservice;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.Map;

@SpringBootApplication
public class AccountServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountServiceApplication.class, args);
	}

	@Bean
	public NewTopic notification() {
		return new NewTopic("notification", 2, (short) 1);
	}

	@Bean
	public NewTopic statistic() {
		return new NewTopic("statistic", 1, (short) 1);
	}

}

package com.application;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;


@SpringBootApplication
@EnableFeignClients
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public NewTopic finish_registration() {
		return TopicBuilder.name("finish-registration")
				.partitions(10)
				.replicas(1)
				.build();
	}
	@Bean
	public NewTopic create_documents() {
		return TopicBuilder.name("create-documents")
				.partitions(10)
				.replicas(1)
				.build();
	}
	@Bean
	public NewTopic send_documents() {
		return TopicBuilder.name("send-documents")
				.partitions(10)
				.replicas(1)
				.build();
	}
	@Bean
	public NewTopic send_ses() {
		return TopicBuilder.name("send-ses")
				.partitions(10)
				.replicas(1)
				.build();
	}
	@Bean
	public NewTopic credit_issued() {
		return TopicBuilder.name("credit-issued")
				.partitions(10)
				.replicas(1)
				.build();
	}
	@Bean
	public NewTopic application_denied() {
		return TopicBuilder.name("application-denied")
				.partitions(10)
				.replicas(1)
				.build();
	}

}

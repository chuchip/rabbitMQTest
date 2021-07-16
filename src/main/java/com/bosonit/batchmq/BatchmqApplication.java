package com.bosonit.batchmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.concurrent.TimeUnit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
@SpringBootApplication
@EnableBatchProcessing
public class BatchmqApplication {

	public static void main(String[] args) {
		SpringApplication.run(BatchmqApplication.class, args);
	}

}

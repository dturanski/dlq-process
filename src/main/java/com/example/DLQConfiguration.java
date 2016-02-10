package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
public class DLQConfiguration {

	private static Logger logger = LoggerFactory.getLogger(DLQConfiguration.class);

	@Autowired
	ConnectionFactory connectionFactory;

	@Autowired
	RabbitTemplate rabbitTemplate;

	@Value("${queue}")
	private String queueName;

	@Value("${maxSize:1}")
	private int maxSize;

	@Bean
	@Autowired
	RabbitAdmin rabbitAdmin() {
		RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
		return rabbitAdmin;
	}

	@Bean RabbitQueueMonitor rabbitQueueMonitor() {
		return new RabbitQueueMonitor(rabbitAdmin());
	}

	@Bean DLQDecider dlqDecider() {
		return new DLQDecider(rabbitQueueMonitor(),
				queueName, maxSize);
	}

	@Bean Tasklet dlqDrainTasklet() {
		return new DLQDrainTasklet(rabbitTemplate, queueName);
	}
}

package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.util.Assert;

import java.util.Properties;

/**
 * @author David Turanski
 **/
public class RabbitQueueMonitor {
	private static Logger logger = LoggerFactory.getLogger(RabbitQueueMonitor.class);
	private RabbitAdmin admin;

	public RabbitQueueMonitor(RabbitAdmin rabbitAdmin) {
		Assert.notNull(rabbitAdmin,"rabbitAdmin cannot be null");
		this.admin = rabbitAdmin;
	}

	public int getQueueSize(String queueName) {
		logger.debug("checking queue {}", queueName);
		Properties properties = admin.getQueueProperties(queueName);
		if (properties == null) {
			logger.warn("queue {} does not exist");
			return -1;
		}

		return (Integer)properties.get(RabbitAdmin.QUEUE_MESSAGE_COUNT);
	}

}

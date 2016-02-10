package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

/**
 * @author David Turanski
 **/
public class DLQDecider implements JobExecutionDecider {

	private final RabbitQueueMonitor rabbitQueueMonitor;

	private final String queueToMonitor;

	private static Logger logger = LoggerFactory.getLogger(DLQDecider.class);

	private final int maxSize;

	public DLQDecider(RabbitQueueMonitor rabbitClient, String queueToMonitor, int
			maxSize) {
		this.rabbitQueueMonitor = rabbitClient;
		this.queueToMonitor = queueToMonitor;
		this.maxSize = maxSize;
	}

	@Override public FlowExecutionStatus decide(JobExecution jobExecution,
			StepExecution stepExecution) {

		int size = rabbitQueueMonitor.getQueueSize(queueToMonitor);

		if (size >= maxSize){
			logger.info("Current queue size {} >= maximum queue {}. Messages will be "
					+ "processed",
					size, maxSize);
			return FlowExecutionStatus.UNKNOWN;
		}

		logger.info("Current queue size is {}. Messages will not be processed", size);
		return FlowExecutionStatus.COMPLETED;
	}
}

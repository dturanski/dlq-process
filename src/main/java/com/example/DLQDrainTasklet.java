package com.example;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.batch.core.*;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

/**
 * @author David Turanski
 **/
public class DLQDrainTasklet implements Tasklet, StepExecutionListener {
	private final RabbitTemplate rabbitTemplate;
	private final String queue;

	@Override public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {

		// Process messages here
		while (rabbitTemplate.receive(queue) != null) {};
		return RepeatStatus.FINISHED;
	}

	public DLQDrainTasklet(RabbitTemplate rabbitTemplate, String queue) {
		this.rabbitTemplate = rabbitTemplate;
		this.queue = queue;
	}

	@Override public void beforeStep(StepExecution stepExecution) {

	}

	@Override public ExitStatus afterStep(StepExecution stepExecution) {
		return ExitStatus.COMPLETED;
	}
}

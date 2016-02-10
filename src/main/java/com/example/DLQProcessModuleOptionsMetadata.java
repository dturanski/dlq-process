package com.example;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.xd.dirt.modules.metadata.RabbitConnectionMixin;
import org.springframework.xd.module.options.spi.Mixin;
import org.springframework.xd.module.options.spi.ModuleOption;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @author David Turanski
 **/
@Mixin({ RabbitConnectionMixin.class })
public class DLQProcessModuleOptionsMetadata {

	private String queue;

	private int maxSize = 1;

	@NotBlank
	public String getQueue() {
		return queue;
	}

	@ModuleOption("the queue from which messages will be processed")
	public void setQueue(String queue) {
		this.queue = queue;
	}
	@Max(1000)
	@Min(1)
	public int getMaxSize() {
		return maxSize;
	}

	@ModuleOption("the maximum size of the queue before messages will be processed")
	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}
}

package com.goldenraspberryawards.apirest.dto;

import java.util.List;

public class IntervalAwardsDTO {
	
	private List<IntervalAwardsProducerDTO> min;
	
	private List<IntervalAwardsProducerDTO> max;
	

	public List<IntervalAwardsProducerDTO> getMin() {
		return min;
	}

	public void setMin(List<IntervalAwardsProducerDTO> min) {
		this.min = min;
	}

	public List<IntervalAwardsProducerDTO> getMax() {
		return max;
	}

	public void setMax(List<IntervalAwardsProducerDTO> max) {
		this.max = max;
	}
	
	
}

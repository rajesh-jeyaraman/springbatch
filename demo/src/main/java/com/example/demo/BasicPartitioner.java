package com.example.demo;

import org.springframework.batch.core.partition.support.SimplePartitioner;
import org.springframework.batch.item.ExecutionContext;

import java.util.HashMap;
import java.util.Map;

public class BasicPartitioner extends SimplePartitioner {

	private static final String PARTITION_KEY = "partition";

//	@Override
//	public Map<String, ExecutionContext> partition(int gridSize) {
//		Map<String, ExecutionContext> partitions = super.partition(gridSize);
//		int i = 0;
//		for (ExecutionContext context : partitions.values()) {
//			context.put(PARTITION_KEY, PARTITION_KEY + (i++));
//		}
//		return partitions;
//	}
	
	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {
		Map<String, ExecutionContext> partitions = super.partition(gridSize);
		gridSize = 3;
	
	    int min = 1;
	    int max = 20;
	    int targetSize = (max - min) / gridSize + 1;
	
	    Map<String, ExecutionContext> result = new HashMap();
	
	    int number = 0;
	    int start = min;
	    int end = start + targetSize - 1;
	    int i = 1;
	    while (start <= max) {
	        ExecutionContext value = new ExecutionContext();
	        	
	        if (end >= max) {
	            end = max;
	        }
	
	        System.out.println("minValue:" + start);
	        System.out.println("minValue:" + end);
	        value.putInt("minValue", start);
	        value.putInt("maxValue", end);
	        value.putString("Name", "Abhay-" + i++);
	        result.put("partition" + number, value);
	        
	        start += targetSize;
	        end += targetSize;
	
	        number++;
	    }
	    System.out.println("Returning from Partition");
	    return result;
	}

}
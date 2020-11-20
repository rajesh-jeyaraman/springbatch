package com.example.worker;


import java.util.List;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamWriter;

public class TestWriter<T> implements ItemStreamWriter<T>{

	public void open(ExecutionContext executionContext) throws ItemStreamException {
		System.out.println("WRITER - OPEN");
		// TODO Auto-generated method stub
		
	}

	public void update(ExecutionContext executionContext) throws ItemStreamException {
		System.out.println("WRITER - UPPDATE");
		// TODO Auto-generated method stub
		
	}

	public void close() throws ItemStreamException {
		System.out.println("WRITER - CLOSE");
		// TODO Auto-generated method stub
		
	}

	public void write(List<? extends T> items) throws Exception {
		
		List<Integer> num=(List<Integer>) items;
		System.out.println("Printing values");
		for(Integer i:num) {
			System.out.print(i+" ");
		}
		System.out.println("WRITER - WRITE");
		
	}

}

package com.example.worker;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

public class TestReader<T> implements ItemStreamReader<T> {
	List<Integer> num = new ArrayList();
	Iterator<Integer> iterator;
	int min;
	int max;
	String name;

	public void open(ExecutionContext executionContext) throws ItemStreamException {
		System.out.println("READER - READ OPEN");
		System.out.println("OPEN "+executionContext.get("minValue")+"  "+executionContext.get("maxValue"));
//		//System.out.println(Thread.currentThread().getName());
//		name=(String)executionContext.get("Name");
//		min = (Integer) executionContext.get("minValue");
//		max = (Integer) executionContext.get("maxValue");
//		for (int i = min; i <= max; i++) {
//			num.add(i);
//		}
//		iterator = num.iterator();

	}

	public void update(ExecutionContext executionContext) throws ItemStreamException {
		System.out.println("READER - READ CLOSE");
		// TODO Auto-generated method stub

	}

	public void close() throws ItemStreamException {
		System.out.println("READER - READ CLOSE");
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unchecked")
	public T read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		System.out.println("READER - READ Worker Name "+name);
//		if (iterator.hasNext()) {
//			T a=(T) iterator.next();
//			//System.out.println("Returning"+a);
//			return a;
//		}
		return null;
	}

}

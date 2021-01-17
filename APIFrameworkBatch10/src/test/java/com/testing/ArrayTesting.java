package com.testing;

import java.util.Arrays;
import java.util.stream.Stream;

public class ArrayTesting {
	
	public static void main(String[] args) {
		// any math int /double/long
		String [] age ={"40","50","60"};

		
		int[] arr = Stream.of(age).mapToInt(Integer::parseInt).toArray();
		System.out.println(Arrays.toString(arr));
		
		
		
		
	}

}

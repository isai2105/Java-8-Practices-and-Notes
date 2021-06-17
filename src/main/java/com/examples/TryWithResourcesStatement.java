package com.examples;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TryWithResourcesStatement {

	/*
	 * IMPORTANTE NOTE
	 * 
	 * We do not have to close the resource explicitly
	 * */
	public static String processFile() throws IOException {
		try (BufferedReader br =
				new BufferedReader(new FileReader("data.txt"))) {
			// NOTE that the following line could change its behavior, but the surrounding
			// should be the same (opening and safely closing the resources)
			// So, with the "execute around" pattern we execute a behavior around some code that does not change
			return br.readLine();
		}
	}
	
	public static String processFileWithLambda(BufferedReaderInterface bb) throws IOException {
		try (BufferedReader br =
				new BufferedReader(new FileReader("data.txt"))) {
			// NOTE that the following line could change its behavior, but the surrounding
			// should be the same (opening and safely closing the resources)
			// So, with the "execute around" pattern we execute a behavior around some code that does not change
			return bb.process(br);
		}
	}
	
	@FunctionalInterface
	public interface BufferedReaderInterface {
		String process(BufferedReader b) throws IOException;
	}
	
	public static void main(String[] args)  {
		try {
			String twoLines = 
					processFileWithLambda(
							(BufferedReader br) -> br.readLine() + br.readLine());
			
			String threeLines = 
					processFileWithLambda(
							(BufferedReader br) -> br.readLine() + br.readLine() + br.readLine());
		} catch(IOException ex) {
			System.out.println("The file: 'data.txt' does not exist ...");
		}
	}
	
}

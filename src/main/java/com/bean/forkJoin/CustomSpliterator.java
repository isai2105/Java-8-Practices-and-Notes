package com.bean.forkJoin;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class CustomSpliterator {

	public static void main(String args[]) {
		/*
		 * Goal: To count words in a string
		 * */
		final String SENTENCE =
				" Nel mezzo del cammin di nostra vita " +
				"mi ritrovai in una selva oscura" +
				" ché la dritta via era smarrita ";
		// Approach #1" Iteratively
		System.out.println("Found " + countWordsIteratively(SENTENCE) + " words");
		
		// Approach #2 Using Stream reduction
		Stream<Character> stream = IntStream.range(0, SENTENCE.length())
				.mapToObj(SENTENCE::charAt);
		System.out.println("Found " + countWords(stream) + " words");
		Stream<Character> stream2 = IntStream.range(0, SENTENCE.length())
				.mapToObj(SENTENCE::charAt);
		// Because the stream is divided in arbitrary positions
		// sometimes a word is divided in two and then counted twice
		System.out.println("Trying approach #s in parallel ... " + countWords(stream2.parallel()) + " words");
		
		// Approach #3 Custom Spliterator<String>
		Spliterator<Character> spliterator = new WordCounterSpliterator(SENTENCE);
		Stream<Character> streamFromCustomSpliterator = StreamSupport.stream(spliterator, true);
		System.out.println("Found " + countWords(streamFromCustomSpliterator) + " words");
	}
	
	private static int countWordsIteratively(String s) {
		int counter = 0;
		boolean lastSpace = true;
		for (char c : s.toCharArray()) {
			if (Character.isWhitespace(c)) {
				lastSpace = true;
			} else {
			if (lastSpace) counter++;
				lastSpace = false;
			}
		}
		return counter;
	}
	
	private static int countWords(Stream<Character> stream) {
		WordCounter wordCounter = stream.reduce(new WordCounter(0, true),
		WordCounter::accumulate,
		WordCounter::combine);
		return wordCounter.getCounter();
	}
	
	static class WordCounter {
		
		private final int counter;
		private final boolean lastSpace;
		
		public WordCounter(int counter, boolean lastSpace) {
			this.counter = counter;
			this.lastSpace = lastSpace;
		}
		
		public WordCounter accumulate(Character c) {
			if (Character.isWhitespace(c)) {
				return lastSpace ?
						this :
							new WordCounter(counter, true);
			} else {
				return lastSpace ?
						new WordCounter(counter+1, false) :
							this;
			}
		}
		
		public WordCounter combine(WordCounter wordCounter) {
			return new WordCounter(counter + wordCounter.counter,
					wordCounter.lastSpace);
		}
		
		public int getCounter() {
			return counter;
		}
		
	}
	
	static class WordCounterSpliterator implements Spliterator<Character> {
		
		private final String string;
		private int currentChar = 0;
		
		public WordCounterSpliterator(String string) {
			this.string = string;
		}
		
		@Override
		public boolean tryAdvance(Consumer<? super Character> action) {
			action.accept(string.charAt(currentChar++));
			// return true if there are further characters to be iterated / consumed
			return currentChar < string.length();
		}
		
		// Defined the logic used to split the data structure to be iterated
		@Override
		public Spliterator<Character> trySplit() {
			// first, we set a limit under which we do not perform further splits
			// ideally, this number is not small as this example .. in order to not create many subtasks
			int currentSize = string.length() - currentChar;
			if (currentSize < 10) {
				return null; // return null when the string is small enough to be processes sequentially
			}
			for (int splitPos = currentSize / 2 + currentChar;
					splitPos < string.length(); splitPos++) {
				// we do not want to perform a split in a middle of a word, but
				// rather; on a whitespace
				if (Character.isWhitespace(string.charAt(splitPos))) {
					Spliterator<Character> spliterator =
							new WordCounterSpliterator(string.substring(currentChar,
									splitPos)); // new counter parsing the string from the start to the split position
					currentChar = splitPos; // set the start position to the split position
					return spliterator;
				}
			}
			return null;
		}
		
		@Override
		public long estimateSize() {
			return string.length() - currentChar;
		}
		
		@Override
		public int characteristics() {
			// ORDERED (because a string is characters in order)
			// SIZED (the value of the estimateSize() method is exact)
			// SUBSIZED (the other SPliterator created by trySplit also have an exact size)
			// NONNULL (there can be no NULL characters n the String)
			// IMMUTABLE (The STring itself is an immutable class)
			return ORDERED + SIZED + SUBSIZED + NONNULL + IMMUTABLE;
		}
		
	}
}


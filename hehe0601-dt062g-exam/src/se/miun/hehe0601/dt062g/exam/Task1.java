package se.miun.hehe0601.dt062g.exam;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

/**
 * This is a test class that will make sure the wordRandomizer works as desired.
 * 
 * @author Henrik Henriksson
 * @version 1.0
 * @since 2020-01-17
 *
 */
public class Task1 {

	public Task1() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {

		String inputString = "En vetenskaplig? undersökning. gjord! vid ett universitet i England har visat att utifall den första och den sista bokstaven i alla ord i en text är riktigt placerade, spelar det ingen roll i vilken ordningsföljd de övriga bokstäverna i orden kommer. Texten är fullt läsbar till och med om de andra bokstäverna kommer hullerombuller.";
		// normal test
		String outputStr = WordRandomizer.randomize(inputString);
		System.out.println(outputStr);

		try {
			// test with cbuf, off, len:
			testcharArray(inputString);
			// test with string, off, len
			testString1(inputString);
			// test with whole string
			testString2(inputString);
		} catch (IOException e) {
			System.out.println("Error during testing!");
			e.printStackTrace();
		}

	}

	// this function will create a char array from an input string and pass it
	// to the filterwriter(c[] cbuf, off, len) method
	public static void testcharArray(String str) throws IOException {

		System.out.println("\n\nTest FilterWriter char[]");
		WordRandomizerFilterWriter fw = null;
		Writer w = null;
		// create a charArray
		char[] cbuf = str.toCharArray();
		String result = null;

		// assign a writer:
		w = new StringWriter();

		// assign writer to filterwriter
		fw = new WordRandomizerFilterWriter(w);

		try {
			// This should create a substring starting at the third char and run
			// until the 13th
			fw.write(cbuf, 3, 13);
			fw.flush();
			// print the results to console:
			result = w.toString();
			System.out.println("Result: " + result);
		} catch (IOException e) {
			e.printStackTrace();
			// close the readers
		} finally {
			if (w != null) {
				w.close();
			}
			if (fw != null) {
				fw.close();
			}

		}

	}

	// this test will test the filterwriter(str, off,len) method
	public static void testString1(String str) throws IOException {

		System.out.println("\n\nTest FilterWriter String offset");
		WordRandomizerFilterWriter fw = null;
		Writer w = null;
		String result = null;

		w = new StringWriter();

		fw = new WordRandomizerFilterWriter(w);

		try {
			fw.write(str, 3, 13);
			fw.flush();
			result = w.toString();
			System.out.println("Result: " + result);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (w != null) {
				w.close();
			}
			if (fw != null) {
				fw.close();
			}

		}

	}

	// this test will test the selfmade filterwriter(str) method
	public static void testString2(String str) throws IOException {
		System.out.println("Test FilterWriter Whole String");
		WordRandomizerFilterWriter fw = null;
		Writer w = null;
		String result = null;

		w = new StringWriter();

		fw = new WordRandomizerFilterWriter(w);

		try {
			fw.write(str);
			fw.flush();
			result = w.toString();
			System.out.println("Result: " + result);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (w != null) {
				w.close();
			}
			if (fw != null) {
				fw.close();
			}

		}

	}

}

package se.miun.hehe0601.dt062g.exam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This Class contains a static method to scramble input strings while leaving
 * the first and last letters unchanged.
 * 
 * @author Henrik Henriksson
 * @version 1.0
 * @since 2020-01-17
 *
 */
public class WordRandomizer {

	// container used to transform the words.
	private static ArrayList<String> words;

//---------------------------------------------------------------------------
	public static String randomize(String inputString) {

		// call method to separate words
		separateWords(inputString);

		StringBuilder manipulatedStrings = new StringBuilder();

		// loop through the array and randomize each word in turn, add them to
		// the strintbuilder object
		for (String word : words) {
			manipulatedStrings.append(randomizeWord(word) + " ");
		}

		return manipulatedStrings.toString();
	}

//---------------------------------------------------------------------------
	// this method will separate words by spaces and add them to a string
	// ArrayList
	private static void separateWords(String pString) {

		words = new ArrayList<String>();

		// split each word and add to the arrayList
		for (String word : pString.split(" ")) {

			words.add(word);
		}

	}

//---------------------------------------------------------------------------
	private static String randomizeWord(String pString) {

		// Check if null or word shorter than 3 chars, which cannot be
		// scrambeled:
		if (pString == null || pString.length() <= 3) {
			return pString;
		}

		// create a regex pattern
		Pattern stringPattern = Pattern.compile("[^a-z0-9 ]",
				Pattern.CASE_INSENSITIVE);
		// create a matcher to iterate the string for special character
		Matcher matchSpecial = stringPattern.matcher(pString);
		// returns true if special char was found:
		boolean specialCharFound = matchSpecial.find();

		// create a shuffelable arraylist containing all chars in the input
		// string.
		ArrayList<Character> chars = new ArrayList<>(pString.length());
		for (char c : pString.toCharArray()) {
			chars.add(c);
		}

		// if specialchar was found, shuffle from a lower index to account for
		// this:
		if (specialCharFound) {
			Collections.shuffle(chars.subList(1, chars.size() - 2));
		} else {
			Collections.shuffle(chars.subList(1, chars.size() - 1));
		}

		// rebuild the char arrayList to a string:
		StringBuilder sb = new StringBuilder(chars.size());
		for (Character c : chars) {
			sb.append(c);
		}

		return sb.toString();

	}
//---------------------------------------------------------------------------
} // end

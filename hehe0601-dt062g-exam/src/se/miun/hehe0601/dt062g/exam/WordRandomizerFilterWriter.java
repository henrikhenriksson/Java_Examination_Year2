package se.miun.hehe0601.dt062g.exam;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * This class extends filterWriter to also scramble each string using the
 * WordRandomizer.
 * 
 * @author Henrik Henriksson
 * @version 1.0
 * @since 2020-01-17
 *
 */
public class WordRandomizerFilterWriter extends FilterWriter {

	public WordRandomizerFilterWriter(Writer out) {
		super(out);
	}

	// this method takes a cbuf char[] offset and length as parameters. The call
	// to out.write converts the c[] to an string and randomizes it, thus
	// reusing the second method
	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {

		super.write(WordRandomizer.randomize(new String(cbuf)), off, len);
	}

	// This overloaded method takes string object, offset and lenghts, scrambles
	// the string and writes the results.
	@Override
	public void write(String str, int off, int len) throws IOException {

		super.write(WordRandomizer.randomize(str), off, len);
	}

	// this method writes an entire scrambeled string
	public void write(String str) throws IOException {

		super.write(WordRandomizer.randomize(str));
	}

}

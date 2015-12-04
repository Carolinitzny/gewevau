import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileReader {

	
	/**
	 * Reads the specified file line by line. All tokens are converted to uppercase.
	 * @param file
	 * @param lib
	 * @throws FileNotFoundException
	 */
	public void fillLibrary(String file, StateLib lib) throws FileNotFoundException {

		FileInputStream fstream;
		fstream = new FileInputStream(file);

		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

		String lastLine = null;
		String newLine;
		try {
			while ((newLine = br.readLine()) != null) {
				newLine = newLine.toUpperCase();
				lib.handleToken(newLine, lastLine); //creates a new state for new lane (if necessary) and adds a transition to state of lastLine
				lastLine = newLine;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileReader {

	public void fillLibrary(String file, StateLib lib) throws FileNotFoundException {

		FileInputStream fstream;
		fstream = new FileInputStream(file);

		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

		String lastLine = null;
		String newLine;
		try {
			while ((newLine = br.readLine()) != null) {
				newLine = newLine.toUpperCase();
				lib.handleToken(newLine, lastLine);
				lastLine = newLine;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}

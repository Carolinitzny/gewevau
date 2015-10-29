
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;


public class ASCIIReader {

	

	
	public Knoten[][] readFile(String file){
		int row = 0;
		Path path = Paths.get(file);
		System.out.println(path.toAbsolutePath().toString());
        try {
        	List<String> lines = Files.readAllLines(path);
        	Knoten[][] knoten = new Knoten[lines.size()][lines.get(0).length()];
        	for(String s : lines){
        		createKnotenForRow(s, row, knoten);
        	}
        	setNachbarn(knoten);
        	return knoten;

        } catch (IOException ex) {

        }
        return null;
	}
	
	
	public void createKnotenForRow(String s, int row, Knoten[][] knoten){
		for(int i = 0; i < s.length();i++){
			char c = s.charAt(i);
			if(c == 'X'){
				knoten[row][i] = new Knoten(true, false, false);
			}else if(c=='s'){
				knoten[row][i] = new Knoten(false, true, false);
			}
			else if(c=='g'){
				knoten[row][i] = new Knoten(false, false, true);
			}
			else if(c==' '){
				knoten[row][i] = new Knoten(false, false, false);
			}
		}
		
	}
	
	
	public void setNachbarn(Knoten[][] knoten){
		for(int row = 1; row < knoten.length-1; row++){
			for(int collumn = 1; collumn < knoten[0].length; collumn++){
				Knoten k = knoten[row][collumn];
				Knoten obererNachbar = knoten[row-1][collumn];
				Knoten untererNachbar = knoten[row+1][collumn];
				Knoten linkerNachbar = knoten[row][collumn-1];
				Knoten rechterNachbar = knoten[row][collumn+1];
				k.addNachbar(obererNachbar);
				k.addNachbar(untererNachbar);
				k.addNachbar(linkerNachbar);
				k.addNachbar(rechterNachbar);
			}
		}
	}
}

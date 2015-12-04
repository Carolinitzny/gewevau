import java.util.Scanner;



/**
 * This approach is working with state objects representing each word. 
 * Therefore it is not necessary to deal with huge (sparse) matrices.
 * This may be slower but could be more memory effective?
 * @author Steve
 *
 * Sentences are not comparable to natural language, as there is no context at all. 
 * Some words are always followed by the exact same word (because there was only one occurrence in the input file)
 * Sometimes cyclic structures appear if after a couple of words the same word as before appears.
 * 
 */
public class main {

	
	public static final String file = "heiseticker-text.txt";
	//public static final String file = "test.txt";
	public static final String start = "MODELLPOLITIK";
	public static final int length = 100; 
	
	public static void main(String[] args) {
		
		
		
		StateLib lib = new StateLib();
		
		lib.initialiseLib(file);
		
		String key = start.toUpperCase();
		if(!lib.contains(key)){
			System.err.println("No transitions for start word known");
			return;
		}
		
		
		
		
		System.out.println("Enter keyword:");
		
		Scanner s = new Scanner(System.in);
		String line = "";
		while((line = s.nextLine()) != null) {
			line = line.toUpperCase();
			if(!lib.contains(line)){
				System.out.println("Keyword not found.");
			}else{
				generateSequence(line, lib);
			}
		}
	}
	
	
	public static void generateSequence(String key, StateLib lib){
		for (int i = 0; i < length; i++){
			System.out.print(key + " ");
			key = lib.getNextWord(key);
			if(key == null){
				return;
			}
		}
		System.out.println();
	}
}

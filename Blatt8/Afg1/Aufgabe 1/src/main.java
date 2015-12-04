import java.io.FileNotFoundException;


public class main {

	
	public static final String file = "heiseticker-text.txt";
	//public static final String file = "test.txt";
	public static final String start = "LABOUR";
	public static final int length = 100; 
	
	public static void main(String[] args) {
		
		
		
		StateLib lib = new StateLib();
		
		lib.initialiseLib(file);
		
		String key = start;
		if(!lib.contains(key)){
			System.err.println("No transitions for start word known");
			return;
		}
		
		
		for (int i = 0; i < length; i++){
			System.out.print(key + " ");
			key = lib.getNextWord(key);
			if(key == null){
				return;
			}
		}
		
		
	}
}

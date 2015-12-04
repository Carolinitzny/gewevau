import java.io.FileNotFoundException;
import java.util.HashMap;


public class StateLib {

	
	
	private HashMap<String, State> dic;
	
	public StateLib() {
		dic = new HashMap<String, State>();
	}
	
	
	public void initialiseLib(String file){
		try {
			new FileReader().fillLibrary(file, this);
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
			return;
		}
		for(State s : dic.values()){
			s.calcProbabilities();
		}
	}
	
	
	public void handleToken(String newWord, String lastWord){
		if(!contains(newWord)){
			dic.put(newWord, new State(newWord));
		}
		if(lastWord != null){
			dic.get(lastWord).addTransition(newWord);
		}
	}
	
	
	public boolean contains(String key){
		return dic.containsKey(key.toUpperCase());
	}
	
	public String getNextWord(String key){
		if(contains(key)){
			return dic.get(key).getNextWord();
		}
		return null;
	}
	
	public String getRandomWord(){
		return dic.keySet().iterator().next();
	}
}

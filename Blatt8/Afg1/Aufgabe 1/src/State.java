import java.util.HashMap;


public class State {

	private HashMap<String, Integer> transitions;
	private double[] probabilities;
	private String[] words;
	private int totalTransitions;
	
	private String word;
	
	public State(String word) {
		transitions = new HashMap<String, Integer>();
		totalTransitions = 0;
		this.word = word;
	}
	
	
	public void addTransition(String nextWord){
		if(!transitions.containsKey(nextWord)){
			transitions.put(nextWord, 0);
		}
		transitions.put(nextWord, transitions.get(nextWord)+1);
		totalTransitions++;
	}
	
	
	
	public void calcProbabilities(){
		probabilities = new double[transitions.size()];
		words = new String[transitions.size()];
		
		int i = 0;
		for(String key : transitions.keySet()){
			probabilities[i] = (double)transitions.get(key)/(double)totalTransitions;
			words[i] = key;
			i++;
		}
		
	}
	
	
	public String getNextWord(){
		if(totalTransitions==0){
			return null; //if this word has no know next words
		}
		double rand = Math.random();
		double sum = probabilities[0];
		int i = 0;
		while(sum < rand){
			i++;
			sum += probabilities[i];
		}
		return words[i];
	}
	
	
}

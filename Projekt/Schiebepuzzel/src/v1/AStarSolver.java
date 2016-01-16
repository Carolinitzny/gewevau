package v1;

import java.util.PriorityQueue;
import java.util.TreeMap;

public class AStarSolver {
	public static GameState aStar(GameState start) {
		PriorityQueue<GameState> queue = new PriorityQueue<GameState>();
		queue.add(start);
		
		TreeMap<String, GameState> dict = new TreeMap<String, GameState>();
		dict.put(start.getID(), start);
		
		int i = 0;
		while (!queue.isEmpty()) {
			GameState nextState = queue.remove();
			if(i%10000==0)
				System.out.println("Iter: "+ i + " frontier: "+queue.size()+" dict: "+dict.size()+" kosten:" + nextState.getCost() +" restkosten:" + nextState.getRestkosten());
			i++;
			if (nextState.getRestkosten() == 0) {
				return nextState;
			}
			for (GameState neighbour : nextState.getNeighbours()) {
				if(!dict.containsKey(neighbour.getID())){
					queue.add(neighbour);
					dict.put(neighbour.getID(), neighbour);
				}
			}
		}
		return null;
	}

	
	public static GameState biDirAStar(GameState start) {
		PriorityQueue<GameState> queue = new PriorityQueue<GameState>();
		queue.add(start);
		
		//PriorityQueue<GameState> queue = new PriorityQueue<GameState>();
		//queue.add(start);
		
		int i = 0;
		while (!queue.isEmpty()) {
			GameState nextState = queue.remove();

			System.out.println("Iter: "+ i + " "+queue.size()+" kosten:" + nextState.getCost() +" restkosten:" + nextState.getRestkosten());
			i++;
			if (nextState.getRestkosten() == 0) {
				return nextState;
			}
			for (GameState neighbour : nextState.getNeighbours()) {
				queue.add(neighbour);
			}
		}
		return null;
	}

	
}

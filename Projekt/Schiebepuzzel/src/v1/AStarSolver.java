package v1;

import java.util.PriorityQueue;
import java.util.TreeMap;

public class AStarSolver {
	public static GameState aStar(GameState start, int outputIntervall) {
		PriorityQueue<GameState> queue = new PriorityQueue<GameState>();
		queue.add(start);
		
		TreeMap<String, GameState> dict = new TreeMap<String, GameState>();
		dict.put(start.getID(), start);
		
		int i = 0;
		while (!queue.isEmpty()) {
			GameState nextState = queue.remove();
			if(i%outputIntervall==0)
				System.out.println("Iter: "+ i + " frontier Forward: "+queue.size()+" dict: "+dict.size()+"	(" + nextState.getCost() +"|" + nextState.getRestkosten()+"|"+(nextState.getCost() + nextState.getRestkosten())+")");
			i++;
			if (nextState.getRestkosten() == 0) {
				return nextState;
			}
			for (GameState neighbour : nextState.getNeighbours()) {
				GameState actState = dict.get(neighbour.getID());
				if(actState == null){
					queue.add(neighbour);
					dict.put(neighbour.getID(), neighbour);
				}else{
					//if(actState.getCost()+actState.getRestkosten()>neighbour.getCost()+neighbour.getRestkosten()){
					//	dict.remove(actState.getID());
					//	queue.remove(actState);
					//	queue.add(neighbour);
					//	dict.put(neighbour.getID(), neighbour);
					//}
				}
			}
		}
		return null;
	}

	
	public static GameState biDirAStar(GameState start, int outputIntervall) {
		PriorityQueue<GameState> queue = new PriorityQueue<GameState>();
		queue.add(start);
		
		PriorityQueue<GameState> backQueue = new PriorityQueue<GameState>();
		backQueue.add(new GameState(GameState.END.getGameState(), null, 0));
		
		TreeMap<String, GameState> dict = new TreeMap<String, GameState>();
		dict.put(start.getID(), start);
		dict.put(GameState.END.getID(), GameState.END);
		
		int i = 0;
		while (!queue.isEmpty() && !backQueue.isEmpty()) {
			
			//forward move
			GameState nextState = queue.remove();
			if (nextState.getRestkosten() == 0) {
				return nextState;
			}
			
			if(i%outputIntervall==0)
				System.out.println("Iter: "+ i + "	frontierForward: "+queue.size()+" frontierBackward: "+backQueue.size()+"	dict: "+dict.size()+"	(" + nextState.getCost() +"|" + nextState.getRestkosten()+"|"+(nextState.getCost() + nextState.getRestkosten())+")");
			i++;
			
			for (GameState neighbour : nextState.getNeighbours()) {
				GameState actState = dict.get(neighbour.getID());
				if(actState == null){ //state wurde noch nicht entdeckt
					neighbour.setForward(true);
					queue.offer(neighbour);
					dict.put(neighbour.getID(), neighbour);
				}else{ //state wurde bereits entdeckt
					if(actState.isBackward()){ //und zwar von back search
						return invertQueue(nextState,actState);
					}
				}
			}
			
			//backward move
			
			nextState = backQueue.remove();
			
			for (GameState neighbour : nextState.getNeighbours()) {
				GameState actState = dict.get(neighbour.getID());
				if(actState == null){ //state wurde noch nicht entdeckt
					neighbour.setBachward(true);
					backQueue.offer(neighbour);
					dict.put(neighbour.getID(), neighbour);
				}else{ //state wurde bereits entdeckt
					if(actState.isForward()){ //und zwar von back search
						return invertQueue(actState,nextState);
					}
				}
			}
			
		}
		return null;
	}
	
	
	
	private static GameState invertQueue(GameState forward, GameState backward){
		GameState predecessor = forward;
		GameState actState = backward;
		GameState follower = actState.getPredecessor();
		while(follower!=null){ //dann drehe die vorgängerkette um
			actState.setPredecessor(predecessor);
			actState.setCost(predecessor.getCost()+1);
			predecessor = actState;
			actState = follower;
			follower = follower.getPredecessor();
		}
		actState.setPredecessor(predecessor);
		actState.setCost(predecessor.getCost()+1);
		return actState;
	}
	
}

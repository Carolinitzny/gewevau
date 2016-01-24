package v1;

import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class AStarSolver {
	
	/**
	 * Starts AStar algorithm at the specified start state.
	 * @param start startState
	 * @param outputIntervall stepsize fordebug output.
	 * @return returns the target state, solution can be reconstructed via state.getPredecessor
	 */
	public static GameState aStar(GameState start, int outputIntervall) {
		PriorityQueue<GameState> queue = new PriorityQueue<GameState>();
		queue.add(start);
		
		TreeMap<Integer, GameState> dict = new TreeMap<Integer, GameState>();
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
				}
			}
		}
		return null;
	}

	
	/**
	 * Starts AStar both at the start and target state. Uses heuristic for both directions, 
	 * already discovered states are saved in a dictionary (red-black tree).
	 * @param start startState
	 * @param outputIntervall stepsize for debug output.
	 * @return returns the target state, solution can be reconstructed via state.getPredecessor
	 */
	public static GameState biDirAStar(GameState start, int outputIntervall) {
		PriorityQueue<GameState> queue = new PriorityQueue<GameState>();
		queue.add(start);
		
		PriorityQueue<GameState> backQueue = new PriorityQueue<GameState>();
		backQueue.add(new GameState(Main.END.getGameState(), null, 0, false));
		
		TreeMap<Integer, GameState> dict = new TreeMap<Integer, GameState>();
		dict.put(start.getID(), start);
		dict.put(Main.END.getID(), Main.END);
		
		int i = 0;
		while (!queue.isEmpty() && !backQueue.isEmpty()) {
			
			//forward move
			GameState nextState = queue.remove();
			if (nextState.getRestkosten() == 0) {
				return nextState;
			}
			nextState.setClosed(true);
			
			if(i%outputIntervall==0)
				System.out.println("Iter: "+ i + "	frontierForward: "+queue.size()+" frontierBackward: "+backQueue.size()+"	dict: "+dict.size()+"	(" + nextState.getCost() +"|" + nextState.getRestkosten()+"|"+(nextState.getCost() + nextState.getRestkosten())+")");
			i++;
			
			for (GameState neighbour : nextState.getNeighbours()) {
				GameState actState = dict.get(neighbour.getID());
				if(actState == null){ //wenn: Nachbarstate wurde noch nicht entdeckt
					neighbour.setForward(true); //Nachbarn in queue aufnehmen
					queue.offer(neighbour);
					dict.put(neighbour.getID(), neighbour);
				}else{  //wenn: nachbarstate wurde schon entdeckt
					if(actState.isBackward()&& actState.isClosed()){ //und zwar von back search
						return invertQueue(nextState,actState); //ergebnis ausgeben, sonst nichts
					}
				}
			}
			
			
			//backward move			
			nextState = backQueue.remove();

			nextState.setClosed(true);
			
			for (GameState neighbour : nextState.getNeighbours()) {
				GameState actState = dict.get(neighbour.getID());
				if(actState == null){ //wenn: Nachbarstate wurde noch nicht entdeckt
					neighbour.setBachward(true); //Nachbarn in queue aufnehmen
					backQueue.offer(neighbour);
					dict.put(neighbour.getID(), neighbour);
				}else{ //wenn: nachbarstate wurde schon entdeckt
					if(actState.isForward() && actState.isClosed()){ //und zwar von vorwärtssuche
						return invertQueue(actState,nextState); //ergebnis ausgeben, sonst nichts
					}
				}
			}
			
		}
		return null;
	}
	
	
	
	public static GameState HashMapAStar(GameState start, int outputIntervall){
		PriorityQueue<GameState> open = new PriorityQueue<GameState>();
		HashMap<Integer, GameState> closed= new HashMap<Integer, GameState>();
		PriorityQueue<GameState> backOpen = new PriorityQueue<GameState>();
		HashMap<Integer, GameState> backClosed= new HashMap<Integer, GameState>();
		
		
		
		open.offer(start);
		backOpen.offer(new GameState(Main.END.getGameState(), null, 0,false));
		
		while((!open.isEmpty()) && (!backOpen.isEmpty())){
			
			GameState current = open.poll();
			closed.put(current.getID(), current);
			
			
			for (GameState neighbour : current.getNeighbours()) {
				
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

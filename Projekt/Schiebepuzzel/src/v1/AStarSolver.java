package v1;

import java.util.PriorityQueue;

public class AStarSolver {
	public static GameState aStar(GameState start) {
		PriorityQueue<GameState> queue = new PriorityQueue<GameState>();
		queue.add(start);
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

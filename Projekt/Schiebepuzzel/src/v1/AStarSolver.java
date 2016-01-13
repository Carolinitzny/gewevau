package v1;

import java.util.PriorityQueue;

public class AStarSolver {
	public static GameState aStar(GameState start) {
		PriorityQueue<GameState> queue = new PriorityQueue<GameState>();
		while (true) {
			GameState nextState = queue.remove();
			if (nextState == null) {
				return null;
			}
			if (nextState.getRestkosten() == 0) {
				return nextState;
			}
			for (GameState neighbour : nextState.getNeighbours()) {
				queue.add(neighbour);
			}
		}
	}

}

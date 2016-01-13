package v1;

import java.util.ArrayList;
import java.util.List;

public class GameState implements Comparable<GameState> {

	@Override
	public int compareTo(GameState other) {
		int total = this.getCost() + this.getRestkosten();
		int totalOther = other.getCost() + other.getRestkosten();

		return totalOther - total;
	}

	
	public int getRestkosten() {
		// TODO Auto-generated method stub
		return 0;
	}


	public int getCost() {
		// TODO Auto-generated method stub
		return 0;
	}


	public List<GameState> getNeighbours() {
		List<GameState> result = new ArrayList<GameState>();
		
		GameState state = this.up();
		if (state != null) {
			result.add(state);
		}

		state = this.down();
		if (state != null) {
			result.add(state);
		}
		
		state = this.left();
		if (state != null) {
			result.add(state);
		}
		
		state = this.right();
		if (state != null) {
			result.add(state);
		}
		return result;
	}


	private GameState right() {
		// TODO Auto-generated method stub
		return null;
	}


	private GameState left() {
		// TODO Auto-generated method stub
		return null;
	}


	private GameState down() {
		// TODO Auto-generated method stub
		return null;
	}


	private GameState up() {
		// TODO Auto-generated method stub
		return null;
	}


	public int[][] getGameState() {
		// TODO Auto-generated method stub
		return null;
	}

}

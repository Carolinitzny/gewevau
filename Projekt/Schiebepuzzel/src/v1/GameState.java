package v1;

import java.util.ArrayList;
import java.util.List;

public class GameState implements Comparable<GameState> {

	private int[][] gamestate;
	private int cost;
	private int restkosten;
	private GameState predecessor;
	private int[] freeTile;
	
	
	public GameState(int[][] init, GameState predecessor, int kosten){
		gamestate=init;
		this.predecessor = predecessor ;
		cost = kosten;
		freeTile = findFreeTile();
		restkosten = HeuristikCalculator.getRestkosten(this);
	}

	private int[] findFreeTile() {
		for (int x = 0; x < gamestate.length; ++x){
			for (int y = 0; y < gamestate[x].length ; ++y){
				if (gamestate[x][y] == 0){
					int[] koordinaten = { x , y };
					return koordinaten; 
					}
			}
		}
		return null;
	}

	@Override
	public int compareTo(GameState other) {
		int total = cost + restkosten;
		int totalOther = other.getCost() + other.getRestkosten();

		return totalOther - total;
	}

	
	public int getRestkosten() {
		return restkosten;
	}


	public int getCost() {
		return cost;
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
		if (freeTile[1] == gamestate[0].length - 1){
			return null; }
		else{
			int[][] gamestateNew = copy(gamestate);
			int x = freeTile[0];
			int y = freeTile[1];
			gamestateNew[x][y] = gamestateNew[x][y+1];
			gamestateNew[x][y+1] = 0;
			return new GameState(gamestateNew, this, ++cost);
		}		
	}

	private GameState left() {
		if (freeTile[1] == 0){
			return null; }
		else{
			int[][] gamestateNew = copy(gamestate);
			int x = freeTile[0];
			int y = freeTile[1];
			gamestateNew[x][y] = gamestateNew[x][y-1];
			gamestateNew[x][y-1] = 0;
			return new GameState(gamestateNew, this, ++cost);
		}
	}


	private GameState down() {
		if (freeTile[0] == gamestate.length){
			return null; }
		else{
			int[][] gamestateNew = copy(gamestate);
			int x = freeTile[0];
			int y = freeTile[1];
			gamestateNew[x][y] = gamestateNew[x-1][y];
			gamestateNew[x-1][y] = 0;
			return new GameState(gamestateNew, this, ++cost);
		}	
	}


	private GameState up() {
		if (freeTile[0] == 0){
			return null; }
		else{
			int[][] gamestateNew = copy(gamestate);
			int x = freeTile[0];
			int y = freeTile[1];
			gamestateNew[x][y] = gamestateNew[x+1][y];
			gamestateNew[x+1][y] = 0;
			return new GameState(gamestateNew, this, ++cost);
		}	
	}

	/**
	 * 
	 * @return den Spielstand in 2D-Array-Darstellung
	 */
	public int[][] getGameState() {
		return gamestate;
	}
	
	private int[][] copy(int[][] arr) {
		int[][] neu = new int[arr.length][arr[0].length];
		for (int x = 0; x < neu.length; ++x){
			for (int y = 0; y < neu[x].length ; ++y){
				neu[x][y] = arr[x][y];
			}
		}
		return neu;
	}

	public GameState getPredecessor() {
		return predecessor;
	}

}

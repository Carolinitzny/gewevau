package v1;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.BackingStoreException;

public class GameState implements Comparable<GameState> {

	private byte[][] gamestate;
	private int cost;
	private int restkosten;
	private GameState predecessor;
	private int[] freeTile;
	private boolean closed;
	
	/**
	 * booleans indicating wether this gamestate has been discovere by the forward or backward A*
	 */
	private boolean forward, bachward;



	public GameState(byte[][] init, GameState predecessor, int kosten, boolean forward) {
		gamestate = init;
		this.predecessor = predecessor;
		cost = kosten;
		freeTile = findFreeTile();

		
		if(forward ){
			this.forward = true; 
			restkosten = HeuristikCalculator.getRestkosten(this);
		}else{ 
				
			this.bachward = true; 
			restkosten = HeuristikCalculator.getRestkostenToStart(Main.START, this);
		}
		
	}

	
	public int[] findTile(int ID){
		for (int x = 0; x < gamestate.length; ++x) {
			for (int y = 0; y < gamestate[x].length; ++y) {
				if (gamestate[x][y] == ID) {
					int[] koordinaten = { x, y };
					return koordinaten;
				}
			}
		}
		return null;
		
	}
	
	private int[] findFreeTile() {
		return findTile(0);
	}

	
	/**
	 * Used for priority queues.
	 * If two states have the same combined value (heuristic +cost), then comparison is only done by real costs, 
	 * to prefer states that are close to the solution
	 */
	@Override
	public int compareTo(GameState other) {

		int total = cost + restkosten;
		int totalOther = other.getCost() + other.getRestkosten();

		if (totalOther - total != 0) {
			return (-1) * (totalOther - total);
		} else {
			return other.getCost() - cost;
		}

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

	public GameState right() {
		if (freeTile[1] == gamestate[0].length - 1) {
			return null;
		} else {
			byte[][] gamestateNew = copy(gamestate);
			int x = freeTile[0];
			int y = freeTile[1];
			gamestateNew[x][y] = gamestateNew[x][y + 1];
			gamestateNew[x][y + 1] = 0;
			return new GameState(gamestateNew, this, cost + 1, forward);
		}
	}

	public GameState left() {
		if (freeTile[1] == 0) {
			return null;
		} else {
			byte[][] gamestateNew = copy(gamestate);
			int x = freeTile[0];
			int y = freeTile[1];
			gamestateNew[x][y] = gamestateNew[x][y - 1];
			gamestateNew[x][y - 1] = 0;
			return new GameState(gamestateNew, this, cost + 1, forward);
		}
	}

	public GameState down() {
		if (freeTile[0] == gamestate.length - 1) {
			return null;
		} else {
			byte[][] gamestateNew = copy(gamestate);
			int x = freeTile[0];
			int y = freeTile[1];
			gamestateNew[x][y] = gamestateNew[x + 1][y];
			gamestateNew[x + 1][y] = 0;
			return new GameState(gamestateNew, this, cost + 1, forward);
		}
	}

	public GameState up() {
		if (freeTile[0] == 0) {
			return null;
		} else {
			byte[][] gamestateNew = copy(gamestate);
			int x = freeTile[0];
			int y = freeTile[1];
			gamestateNew[x][y] = gamestateNew[x - 1][y];
			gamestateNew[x - 1][y] = 0;
			return new GameState(gamestateNew, this, cost + 1, forward);
		}
	}

	
	public void setPredecessor(GameState predecessor) {
		this.predecessor = predecessor;
	}
	
	public void setCost(int cost) {
		this.cost = cost;
	}
	
	public void setRestkosten(int restkosten) {
		this.restkosten = restkosten;
	}
	/**
	 * 
	 * @return den Spielstand in 2D-Array-Darstellung
	 */
	public byte[][] getGameState() {
		return gamestate;
	}

	private byte[][] copy(byte[][] arr) {
		byte[][] neu = new byte[arr.length][arr[0].length];
		for (int x = 0; x < neu.length; ++x) {
			for (int y = 0; y < neu[x].length; ++y) {
				neu[x][y] = arr[x][y];
			}
		}
		return neu;
	}

	public GameState getPredecessor() {
		return predecessor;
	}

	
	/**
	 * Prints this state nicely looking
	 */
	public void print() {
		
		System.out.println("-------------------------------");
		if(isForward()){
			System.out.println("### FORWARD ###");
		}else if(isBackward()) {
			System.out.println("### BACKWARD ###");
		}
		
		for (int i = 0; i < 4; i++) {
			System.out.println("|" + gamestate[i][0] + "|" + gamestate[i][1] + "|" + gamestate[i][2] + "|"
					+ gamestate[i][3] + "|");
		}
		System.out.println("-------------------------------");
		
	}
	
	
	
	/**
	 * ID is a string combination of all the fields of the array, to hashCode 
	 * @return
	 */
	public int getID(){	
		String ID = "";
		for(int i = 0; i < 16; i++){
			if(gamestate[i/4][i%4] < 10){
				ID += "0"+gamestate[i/4][i%4];
			}else{
				ID += gamestate[i/4][i%4];
			}
		}
		
		return ID.hashCode();
	}
	
	
	public void setForward(boolean forward) {
		this.forward = forward;
	}
	
	public void setBachward(boolean bachward) {
		this.bachward = bachward;
	}
	
	public boolean isForward(){
		return forward;
	}
	
	public boolean isBackward(){
		return bachward;
	}
	
	public void setClosed(boolean closed) {
		this.closed = closed;
	}
	
	public boolean isClosed(){
		return closed;
	}
	
	

	
}

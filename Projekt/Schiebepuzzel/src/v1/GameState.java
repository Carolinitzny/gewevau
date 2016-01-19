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
	
	/**
	 * booleans indicating wether this gamestate has been discovere by the forward or backward A*
	 */
	private boolean forward, bachward;

	public static GameState END = new GameState(new byte[][]{{1, 2, 3, 4},
	                                              {5, 6, 7, 8},
	                                              {9, 10, 11, 12},
	                                              {13, 14, 15, 0}}, null, 0);

	public GameState(byte[][] init, GameState predecessor, int kosten) {
		gamestate = init;
		this.predecessor = predecessor;
		cost = kosten;
		freeTile = findFreeTile();
		restkosten = HeuristikCalculator.getRestkosten(this);
	}

	private int[] findFreeTile() {
		for (int x = 0; x < gamestate.length; ++x) {
			for (int y = 0; y < gamestate[x].length; ++y) {
				if (gamestate[x][y] == 0) {
					int[] koordinaten = { x, y };
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

		return (-1) * (totalOther - total);
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
			return new GameState(gamestateNew, this, cost + 1);
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
			return new GameState(gamestateNew, this, cost + 1);
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
			return new GameState(gamestateNew, this, cost + 1);
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
			return new GameState(gamestateNew, this, cost + 1);
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
	
	
	public String getID(){	
		String ID = "";
		for(int i = 0; i < 16; i++){
			if(gamestate[i/4][i%4] < 10){
				ID += "0"+gamestate[i/4][i%4];
			}else{
				ID += gamestate[i/4][i%4];
			}
		}
		
		return ID;
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
	

}

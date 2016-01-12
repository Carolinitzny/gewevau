package v1;

public class HeuristikCalculator {

	/**
	 * Berechnet die geschätzen Restkosten eines GameStates.
	 * Grundlage der Berechnung ist die Simme der Manhatten Distanzen aller Tiles zu ihren Zielen.
	 * Ziel-Spielfeld:
	 *  _____________
	 * |  1  2  3  4 |
	 * |  5  6  7  8 |
	 * |  9 10 11 12 |
	 * | 13 14 15  0 |
	 * |_____________|
	 * @param state Der aktuelle GameState
	 * @return Summer der Distanzen
	 */
	public static int getRestkosten(GameState state){
		
		int restkosten = 0;
		
		for(int row = 0; row < 4; row++){
			for(int col = 0; col < 4; col++){
				int val = state.getGameState()[row][col];
				if(val != 0){
					int targetRow = (val-1)/4;
					int targetCol = (val-1)%4;
					int rowDif = (int)Math.abs(targetRow - row);
					int colDif = (int)Math.abs(targetCol - col);
					restkosten += rowDif + colDif;
				}
			}
			
		}
		
		
		return restkosten;
	}
}

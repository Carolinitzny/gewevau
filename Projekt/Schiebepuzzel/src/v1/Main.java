package v1;

import v1.gui.GameStateVisualizer;

public class Main {
	public static void main(String[] args) {
		byte[][] gameState1 = {
				{1,2,3,4},
				{5,6,7,8},
				{9,10,11,12},
				{0,13,14,15},			
		};
		
		byte[][] gameState = {
				{1,2,3,4},
				{5,6,7,8},
				{0,10,11,12},
				{13,14,15,15},			
		};
		
		//new GameStateVisualizer(new GameState(gameState,null,0));
		
		GameState target = new AStarSolver().aStar(new GameState(gameState,null,0));
		System.out.println(target.getCost());
		while(target!=null){
			target.print();

			System.out.println(target.getCost());
			target= target.getPredecessor();
		}
		
	}
}

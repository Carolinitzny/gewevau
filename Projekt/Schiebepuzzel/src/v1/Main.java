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
				{9,10,11,12},
				{13,15,14,0},			
		};
		
		//new GameStateVisualizer(new GameState(gameState,null,0));
		GameState start = new PuzzleMaker().makePuzzle(100);
		start.print();
		start.setPredecessor(null);
		start.setCost(0);
		start.setRestkosten(HeuristikCalculator.getRestkosten(start));
		GameState target = AStarSolver.aStar(start);
		System.out.println(target.getCost());
		while(target!=null){
			target.print();

			System.out.println(target.getCost());
			System.out.println();
			target= target.getPredecessor();
		}
		
	}
}

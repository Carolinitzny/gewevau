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
		
		/**
		 * ASTAR: 		Iter: 496 	frontierForward: 507 						dict: 1004	(18|0|18)
		 * BDIRASTAR: 	Iter: 326	frontierForward: 324 frontierBackward: 329	dict: 1306	(10|8|18)
		
		 */
		byte[][] gameState = {
				{1,2,3,4},
				{5,6,7,8},
				{9,10,11,12},
				{15,13,14,0},			
		};
		
		
		/**
		 * 					
		 * Can be solved in 196670 iterations with no lin Conflict.
		 * Pathlength 39
		 */
		byte[][] gameState2 = {
		{3,0,14,4},
		{2,7,5,6},
		{9,1,8,12},
		{15,13,10,11}};
		
		//new GameStateVisualizer(new GameState(gameState,null,0));
		System.out.println("Start state:");
		//GameState start = new PuzzleMaker().makePuzzle(500);
		GameState start = new GameState(gameState, null, 0);
		
		start.print();
		start.setPredecessor(null);
		start.setCost(0);
		start.setForward(true);
		start.setRestkosten(HeuristikCalculator.getRestkosten(start));
		System.out.println();
		GameState target = AStarSolver.biDirAStar(start,10);
		System.out.println(target.getCost());
		while(target!=null){
			target.print();

			System.out.println(target.getCost());
			System.out.println();
			target= target.getPredecessor();
		}
		
	}
}

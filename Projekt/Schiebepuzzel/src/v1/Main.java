package v1;

import v1.gui.GameStateVisualizer;


/**
 * Main class to start the project. Initialise gamestate either with one of the presets or create a new gamestate via PuzzleMaker
 * @author Steve
 *
 */
public class Main {

	
	/**
	 * 1D+0comp:	Iter: 78972 frontierForward: 74518 dict: 153491	(39|0|39)			
	 * 2D+0comp:	Iter: 78969	frontierForward: 74513 frontierBackward: 79989	dict: 312441	(36|3|39)
	 * Pathlength 39
	 */
	static byte[][] gameState = {
	{3,0,14,4},
	{2,7,5,6},
	{9,1,8,12},
	{15,13,10,11}};
	

	
	static byte[][] gameState1 = {
			{1,2,3,4},
			{5,6,7,8},
			{9,10,11,12},
			{0,13,14,15},			
	};
	
	
	/**
	 * ASTAR: 		Iter: 496 	frontierForward: 507 						dict: 1004	(18|0|18)
	 * BDIRASTAR: 	Iter: 326	frontierForward: 324 frontierBackward: 329	dict: 1306	(10|8|18)
	 * 1D+0comp:	Iter: 350 	frontierForward: 356 						dict: 707	(18|0|18)
	 * 2D+0comp:	Iter: 313	frontierForward: 313 frontierBackward: 319	dict: 1259	(13|5|18)
	 * 2D+0comp:	Iter: 71	frontierForward: 82  frontierBackward: 87	dict: 312	(5|9|14)
	 */
	static byte[][] gameState2 = {
			{1,2,3,4},
			{5,6,7,8},
			{9,10,11,12},
			{15,13,14,0},			
	};
	
	

	
	
	
	
	public static GameState START = new GameState(gameState, null, 0, true);
	//public static GameState START = new PuzzleMaker().makePuzzle(500);
	
	public static GameState END = new GameState(new byte[][]{{1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 0}}, null, 0, false);
	
	
	
	
	public static void main(String[] args) {
		
		
	
		//new GameStateVisualizer(new GameState(gameState,null,0));
		System.out.println("Start state:");
		START.print();
		
		//puzzle maker initialises these values, so they need to be reset for AStar
		START.setPredecessor(null);
		START.setCost(0);
		START.setForward(true);
		START.setRestkosten(HeuristikCalculator.getRestkosten(START));
		System.out.println();
		System.out.println("--------------------------------------");
		
		
		
		//too much output slows down the algorithm
		//GameState targetBiDir = AStarSolver.aStar(START,100);
		GameState targetBiDir = AStarSolver.biDirAStar(START,100);
		printLoesungsweg(targetBiDir);
		
		
	}
	
	/**
	 * Prints out each step of the puzzle from target to start state
	 * @param target
	 */
	public static void printLoesungsweg(GameState target) {
		while (target != null) {
			target.print();
			System.out.println(target.getCost());
			System.out.println();
			new GameStateVisualizer(target);
			target = target.getPredecessor();
		}
		
	}
}

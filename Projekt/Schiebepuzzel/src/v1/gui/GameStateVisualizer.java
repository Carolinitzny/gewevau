package v1.gui;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import v1.GameState;

public class GameStateVisualizer {

	
	private JFrame frame;
	private JPanel gridHolder;
	private GameTile[][] gameTiles;
	
	private GameState state;
	
	public GameStateVisualizer(GameState gameState) {
		
		this.state = gameState;
		
		frame = new JFrame();
		frame.setPreferredSize(new Dimension(600,600));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel contentPane = (JPanel)frame.getContentPane();
		
		gameTiles = new GameTile[4][4];
		gridHolder = new JPanel(new GridLayout(4,4));
		contentPane.add(gridHolder);
		
		
		for(int row = 0; row < 4; row++){
			for(int col = 0; col < 4; col ++){
				gameTiles[row][col] = new GameTile(gameState.getGameState()[row][col],4*row+col, this);
				gridHolder.add(gameTiles[row][col]);
			}
		}
		
		
		
		frame.pack();
		frame.setVisible(true);
	}
	
	
	protected void tileClicked(int nr, int pos){
		System.out.println("clicked " + nr);
	}
}

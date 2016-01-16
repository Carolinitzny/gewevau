package v1;

import java.util.List;

public class PuzzleMaker {
	public GameState makePuzzle(int random) {

		GameState puzzle = GameState.END;
		for (int i = 0; i < random; ++i) {
			List<GameState> neighbours = puzzle.getNeighbours();
			puzzle = neighbours.get((int) Math.floor(Math.random() * neighbours.size()));
		}
		return puzzle;

	}
}

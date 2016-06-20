package maze_learning;

public class MazeLearning {
	
	public MazeLearning(){
		Maze maze = new Maze();
		maze.makeMaze();
		maze.calcPath();
		
		int[][] mazeBlock = maze.getMazeBlock();
		int px = 1;
		int py = 1;
		int up = py - 1;
		int right = px + 1;
		int down = py + 1;
		int left = px - 1;
	}
	
	public static void main(String[] args){
		new MazeLearning();
	}

}

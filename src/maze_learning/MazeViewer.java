package maze_learning;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MazeViewer extends JFrame{

	private Maze maze;

	public MazeViewer(Maze maze){
		
		this.maze = maze;

		JPanel mazePanel = new JPanel();
		mazePanel.setBounds(0, 0, 600, 600);

		BlockPanel[][] mazeBlockPanel = new BlockPanel[12][12];

		for(int i = 0; i < 12; i++){
			for(int j = 0; j < 12; j++){
				mazeBlockPanel[i][j] = new BlockPanel(this.maze.getMaze()[i][j]);
				mazeBlockPanel[i][j].setBounds(40 * j + 40, 40 * i + 40, 40, 40);
				mazePanel.add(mazeBlockPanel[i][j]);
				this.getContentPane().add(mazeBlockPanel[i][j]);
			}
		}

		this.getContentPane().add(mazePanel);

		this.setBounds(200, 200, 600, 600);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args){
		Maze maze = new Maze();
		new MazeViewer(maze);
	}
}

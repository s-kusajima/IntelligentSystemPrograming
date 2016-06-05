package maze_learning;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class BlockPanel extends JPanel{

	private int status;

	public BlockPanel(int s){

		this.status = s;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;

		switch(this.status){
		case 0:
			g.setColor(Color.BLACK);
			g.drawRect(0, 0, 39, 39);
			break;
		case 1:
		case 2:
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 39, 39);
			break;
		case 3:
			try{
				BufferedImage image = ImageIO.read(new File("start.png"));
				g.drawImage(image, 0, 0, this);
			}catch(IOException e){
				e.printStackTrace();
			}
			break;
		case 4:
			try{
				BufferedImage image = ImageIO.read(new File("goal.png"));
				g.drawImage(image, 0, 0, this);
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}

	public int getBlockStatus(){

		return this.status;
	}

}

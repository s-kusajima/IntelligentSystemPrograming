package maze_astar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

class makeMaze {
	
	int block[][];
	
	public makeMaze(){
		
		block = new int[12][12];
		
		for(int i = 0; i < 12; i++){
			for(int j = 0; j < 12; j++){
				if(i == 0 || i == 11 || j == 0 || j == 11){
					block[i][j] = 2;
				}else{
					block[i][j] = 1;
				}
			}
		}
		
		int first_x = (new Random()).nextInt(10) + 1;
		int first_y = (new Random()).nextInt(10) + 1;
		
		recur(first_x, first_y);
		
		if(block[1][1] == 1){
			block[1][1] = block[2][1] = block[1][2] = 0;
		}else if(block[10][10] == 1){
			block[10][10] = block[9][10] = block[10][9] = 0;
		}
		
		for(int i = 0; i < 12; i++){
			for(int j = 0; j < 12; j++){
//				System.out.print(block[i][j]);
				if(i == 1 && j == 1){
					System.out.print("S");
				}else if(i == 10 && j == 10){
					System.out.print("G");
				}else if(block[i][j] == 2 || block[i][j] == 1){
					System.out.print("#");
				}else{
					System.out.print(".");
				}
			}
			System.out.println("");
		}
		
	}
	
	public void recur(int x, int y){
		
		block[x][y] = 0;
		
		System.out.println("x:" + x + " y:" + y);
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(0);
		list.add(1);
		list.add(2);
		list.add(3);
		Collections.shuffle(list);
		
		for(int i = 0; i < 4; i++){
			int direction = list.get(i);
			switch(direction){
			case 0:
				if(exists(x, y + 2) && block[x][y + 2] == 1){
					block[x][y + 1] = 0;
					recur(x, y + 2);
				}
				break;
			case 1:
				if(exists(x + 2, y) && block[x + 2][y] == 1){
					block[x + 1][y] = 0;
					recur(x + 2, y);
				}
				break;
			case 2:
				if(exists(x, y - 2) && block[x][y - 2] == 1){
					block[x][y - 1] = 0;
					recur(x, y - 2);
				}
				break;
			case 3:
				if(exists(x - 2, y) && block[x - 2][y] == 1){
					block[x - 1][y] = 0;
					recur(x - 2, y);
				}
				break;
			}
		}	
	}
	
	public boolean exists(int x, int y){
	
		if(x < 0 || x > 11 || y < 0 || y > 11){
			return false;
		}else{
			return true;
		}
	}
	
	public static void main(String[] args){
		new makeMaze();
	}
}
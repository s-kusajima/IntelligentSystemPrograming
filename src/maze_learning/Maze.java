package maze_learning;

import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;


// 穴掘り法で10×10の迷路を自動生成するプログラム．
public class Maze {
	
	private int[][] mazeBlock;
	
	static final int INF = Integer.MAX_VALUE; // INF値
	
	// 4方向探索用
	static final int[] dx = {0, 1, 0, -1};
	static final int[] dy = {-1, 0, 1, 0};
	static final char[] dir = {'u', 'r', 'd', 'l'};
	
	private String path = ""; // 移動経路
	
	public Maze(){
		
		mazeBlock = new int[12][12];
		
		// 迷路ブロックの初期化．外側は変化しない壁(2)，内側はとりあえずの壁(1)．
		for(int i = 0; i < 12; i++){
			for(int j = 0; j < 12; j++){
				if(i == 0 || i == 11 || j == 0 || j == 11){
					mazeBlock[i][j] = 2;
				}else{
					mazeBlock[i][j] = 1;
				}
			}
		}
		
		// 初期位置を決定
		int first_x = (new Random()).nextInt(10) + 1;
		int first_y = (new Random()).nextInt(10) + 1;
		
		recur(first_x, first_y);
		
		// 再帰処理が終わった段階で，StartとGoalどちらかは壁になっているので，その周りを掘る
		if(mazeBlock[1][1] == 1){
			mazeBlock[1][1] = mazeBlock[2][1] = mazeBlock[1][2] = 0;
		}else if(mazeBlock[10][10] == 1){
			mazeBlock[10][10] = mazeBlock[9][10] = mazeBlock[10][9] = 0;
		}
		
		// Startを3に，Goalを4に
		mazeBlock[1][1] = 3;
		mazeBlock[10][10] = 4;
		
		
		// 生成した迷路をコンソールに表示
		for(int i = 0; i < 11; i++){
			for(int j = 0; j < 12; j++){
//				System.out.print(mazeBlock[i][j]);
				
				if(i == 1 && j == 1){
					System.out.print("S");
				}else if(i == 10 && j == 10){
					System.out.print("G");
				}else if(mazeBlock[i][j] == 2 || mazeBlock[i][j] == 1){
					System.out.print("#");
				}else{
					System.out.print(".");
				}
				
			}
			System.out.println("");
		}
		
		
		// 以下，A*探索で最短経路を計算するつもり
		int[][] grid = new int[10][10]; // 移動コスト(距離)の記録
		
		// 迷路データのパース
		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 10; j++){
				if(mazeBlock[i + 1][j + 1] == 1){
					grid[i][j] = -1; // 壁
				}else if(mazeBlock[i + 1][j + 1] == 3){
					grid[i][j] = 0; // スタート：距離0
				}else if(mazeBlock[i + 1][j + 1] == 4){
					grid[i][j] = INF; // ゴール：距離∞
				}else{
					grid[i][j] = INF; // 通路：距離∞
				}
			}
		}
		
		// A*探索
		Queue<Position> q = new PriorityQueue<Position>();
		
		Position p = new Position(0, 0);
		p.estimate = getManhattanDistance(0, 0, 9, 9);
		q.add(p);
		
		while(!q.isEmpty()){
			p = q.poll();
			if(p.cost > grid[p.y][p.x]){
				continue;
			}
			if(p.y == 9 && p.x == 9){
				path = p.path;
				break;
			}
			
			for(int i = 0; i < dx.length; i++){
				int nx = p.x + dx[i];
				int ny = p.y + dy[i];
				if(nx < 0 || 10 <= nx || ny < 0 || 10 <= ny){
					continue;
				}
				if(grid[ny][nx] > grid[p.y][p.x] + 1){
					grid[ny][nx] = grid[p.y][p.x] + 1;
					
					Position p2 = new Position(nx, ny);
					p2.cost = grid[ny][nx];
					p2.estimate = getManhattanDistance(nx, ny, 9, 9) + p2.cost;
					p2.path = p.path + dir[i];
					q.add(p2);
				}
			}
		}
		
		System.out.println("Path:" + this.path);
		
	}
	
	// 再帰的に穴を掘り進む関数
	public void recur(int x, int y){
		
		// 現在のマスを通路に
 		mazeBlock[x][y] = 0;
		
 		// ランダムでチェックする方向を決める．順番はランダムだけど全方向チェックしたいからとりあえず配列で
 		// 配列に4方向(0,1,2,3)を格納し，シャッフル
 		// 0…前 1…右 2…後 3…左
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(0);
		list.add(1);
		list.add(2);
		list.add(3);
		Collections.shuffle(list);
		
		// 4方向に対して再帰的にチェック
		// 指定した方向の2マス先を確認し，そのマスが掘ることのできる通路だったら再帰
		// 1マス先を通路にし，2マス先で再帰的にこの関数を呼び出す
		for(int i = 0; i < 4; i++){
			int direction = list.get(i);
			switch(direction){
			case 0:
				if(exists(x, y + 2) && mazeBlock[x][y + 2] == 1){
					mazeBlock[x][y + 1] = 0;
					recur(x, y + 2);
				}
				break;
			case 1:
				if(exists(x + 2, y) && mazeBlock[x + 2][y] == 1){
					mazeBlock[x + 1][y] = 0;
					recur(x + 2, y);
				}
				break;
			case 2:
				if(exists(x, y - 2) && mazeBlock[x][y - 2] == 1){
					mazeBlock[x][y - 1] = 0;
					recur(x, y - 2);
				}
				break;
			case 3:
				if(exists(x - 2, y) && mazeBlock[x - 2][y] == 1){
					mazeBlock[x - 1][y] = 0;
					recur(x - 2, y);
				}
				break;
			}
		}	
	}
	
	// 指定した座標が存在するかどうかを確認する関数
	public boolean exists(int x, int y){
		
		if(x < 0 || x > 11 || y < 0 || y > 11){
			return false;
		}else{
			return true;
		}
	}
	
	 //マンハッタン距離を求める
    static int getManhattanDistance(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }
    
	//位置情報の構造体
    class Position implements Comparable<Position>{
        int x;               //座標
        int y;
        int cost;            //移動コスト(スタートからの移動量)
        int estimate;        //推定値(ゴールまでのマンハッタン距離＋移動コスト)
        String path = "";    //移動経路(移動方向の記録)

        //コンストラクタ
        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        //比較関数
        @Override
        public int compareTo(Position o) {
            return this.estimate - o.estimate;    //推定値で小さい順
        }
    }
	
	public int[][] getMaze(){
		return this.mazeBlock;
	}
		
	/*
	public static void main(String[] args){
		
		new Maze();
	}
	*/

}

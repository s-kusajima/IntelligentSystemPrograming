package xor;

// http://yasu9780.hatenablog.com/entry/2016/01/31/002233

import java.util.*;

public class BPropagation {
	
	int[] x = new int[3];
	int[] t = new int[3];
	Double[] g = new Double[3];
	Double[] z = new Double[3];
	Double[][] h = new Double[30][30];
	Double[][] w = new Double[30][30];
	
	public Double sigmoid(double a){
		return 1.0 /(1.0 + Math.exp(-a));
	}
	
	public Double dsigmoid(double a){
		return a * (1.0 - a);
	}
	
	public BPropagation(){
		
		Double alpha = 0.06;
		
		for(int j = 0; j <= 2; j++){
			for(int i = 0; i <= 2; i++){
				w[j][i] = (double)(new Random().nextInt(1000)) / 1000;
				h[j][i] = (double)(new Random().nextInt(1000)) / 1000;
			}
		}

		for(int count = 0; count < 100000; count++){
			x[0] = 1;
			x[1] = new Random().nextInt(2);
			x[2] = new Random().nextInt(2);
			
			if(x[1] == x[2]){
				t[0] = 0;
			}else{
				t[0] = 1;
			}
			
			if(x[1] == 1 && x[2] == 1){
				t[1] = 1;
			}else{
				t[1] = 0;
			}
			
			if(x[1] == 0 && x[2] == 0){
				t[2] = 0;
			}else{
				t[2] = 1;
			}
			
			for(int i = 0; i <= 2; i++){
				Double sum = 0.0;
				for(int j = 0; j <= 2; j++){
					sum += w[j][i] * x[j];
				}
				g[i] = sigmoid(sum);
			}
			
			for(int i = 0; i <= 2; i++){
				double sum = 0.0;
				for(int j = 0; j <= 2; j++){
					sum += h[j][i] * g[j];
				}
				z[i] = sigmoid(sum);
			}
			
			alpha = 0.06;
			for(int j = 0; j <= 2; j++){
				for(int i = 0; i <= 2; i++){
					h[i][j] += alpha * g[i] * (t[j] - z[j]) * z[j] * (1.0 - z[j]);
				}
			}
			
			for(int k = 0; k <= 2; k++){
				for(int j = 0; j <= 2; j++){
					double dj = (t[k] - z[k]) * z[k] * (1.0 - z[k]) * h[j][k] * g[j] * (1.0 - g[j]);
					for(int i = 0; i <= 2; i++){
						w[i][j] += alpha * x[i] * dj;
					}
				}
			}
			
			Double error = 0.0;
			for(int i = 0; i <= 2; i++){
				error += (t[i] - z[i]) * (t[i] - z[i]);
			}
			
			if(count % 2000 != 0) continue;
			
			System.out.println(count + " in=[" + x[1] + "," + x[2] + "]"
					+ " out=[XOR=" + z[0] + " AND=" + z[1] + " OR=" + z[2] +"]"
					+ " err=" + error);
			
		}
		
		for(int j = 0; j <= 2; j++){
			for(int i = 0; i <= 2; i++){
				System.out.println("w[" + j + "][" + i + "]=" + w[i][j]);
			}
		}
		
		for(int j = 0; j <= 2; j++){
			for(int i = 0; i <= 2; i++){
				System.out.println("h[" + j + "][" + i + "]=" + h[i][j]);
			}
		}
	}
	
	public static void main(String[] args){
		new BPropagation();
	}

}

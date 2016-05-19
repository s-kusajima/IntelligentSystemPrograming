package neural_network_xor;

// http://yasu9780.hatenablog.com/entry/2016/01/31/002233

import java.io.*;
import java.util.*;

public class BPropagation_demo {
	
	private int[] x;		// input
	private int t;			// teacher signal
	private double[] g;		// output of middle class
	private double z;		// output of output class
	private double[][] w;	// weight between input class and middle class
	private double[] h;		// weight between middle class and output class
	private double alpha;	// learning rate
	
	private BPropagation_demo(int n, double a, int c) throws IOException{
		
		System.out.println("Program begin.");	
		
		x = new int[2];
		g = new double[n];
		w = new double[2][n];
		h = new double[n];
		
		this.alpha = a;
		
		// initialize weight (-1.000 ~ 0.999)
		for(int j = 0; j < n; j++){
			for(int i = 0; i < 2; i++){
				w[i][j] = (double)(new Random().nextInt(2000) - 1000) / 1000;
			}
			h[j] = (double)(new Random().nextInt(2000) - 1000) / 1000;
		}

		// learning
		for(int count = 0; count < c; count++){
			
			// initialize input and teacher signal
			x[0] = new Random().nextInt(2);
			x[1] = new Random().nextInt(2);
			t = (x[0] == x[1]) ? 0 : 1;
			
			// calculate output of middle class
			for(int i = 0; i < n; i++){
				double sum = 0.0;
				for(int j = 0; j < 2; j++){
					sum += w[j][i] * x[j];
				}
				g[i] = sigmoid(sum);
			}
			
			// calculate output of output class
			double sum = 0.0;
			for(int i = 0; i < n; i++){
				sum += h[i] * g[i];
			}
			z = sigmoid(sum);
			
			// learning of output class
			for(int i = 0; i < n; i++){
				h[i] += alpha * g[i] * (t - z) * dsigmoid(z);
			}
			
			// learning of middle class
			for(int j = 0; j < n; j++){
				for(int i = 0; i < 2; i++){
					w[i][j] += alpha * x[i] * (t - z) * dsigmoid(z) * h[j] * dsigmoid(g[j]);
				}
			}

			// measurement error
			double error = Math.abs(t - z);
			System.out.println((count + 1) + " in=[" + x[0] + "," + x[1] + "] out=" + round_five(z) +
					" error=" + round_five(error));
			
		}
		
		/*
		for(int j = 0; j < 2; j++){
			for(int i = 0; i < n; i++){
				System.out.println("w[" + j + "][" + i + "]=" + round_five(w[j][i]));
			}
		}
		
		for(int i = 0; i < n; i++){
			System.out.println("h[" + i + "]=" + round_five(h[i]));
		}
		*/
		
	}
	
	// sigmoid function
	private double sigmoid(double a){
		return 1.0 /(1.0 + Math.exp(-a));
	}
	
	// differential function
	private double dsigmoid(double a){
		return a * (1.0 - a);
	}
	
	private double round_five(Double n){
		int a = (int)(n * 100000);
		return (double)a / 100000;
	}
	
	public static void main(String[] args){
		try{
			new BPropagation_demo(2, 1.0, 100000);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

}

package neural_network_xor;

import java.io.*;

public class CalcAverage {
	
	public CalcAverage(){
		
		System.out.println("Program begin.");
		
		double[] sum = new double[100000];
		
		try{
			File dir = new File("LogFolder");
			File[] files = dir.listFiles();
			for(int i = 0; i < files.length; i++){
				
				System.out.println("File:" + files[i].getName() + " start.");
				
				BufferedReader br = new BufferedReader(new FileReader(files[i]));
				String line;
				int count = 0;
				while((line = br.readLine()) != null){
					sum[count] += Double.parseDouble(line);
					count++;
				}
				br.close();
				
				System.out.println("File:" + files[i].getName() + " end.");
			}
			
			File file = new File(files[0].getName().replace("_0.csv", "_average.csv"));
			
			PrintWriter pw = new PrintWriter(new FileWriter(file));
			for(int i = 0; i < sum.length; i++){
				pw.println(sum[i]/10);
			}
			pw.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		
		System.out.println("Program finish.");
		
	}
	
	public static void main(String[] args){
		new CalcAverage();
	}

}

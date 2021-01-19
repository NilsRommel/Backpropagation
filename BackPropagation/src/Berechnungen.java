import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

/*
	This class is for all Calculations of the Network
*/



public class Berechnungen {
	
	private static double[][] SigmoidPoints;
	
	public Berechnungen() {
		Sigmoid_Funktion_Build_List(1000,"0.000000");
	}
	
	protected static void Sigmoid_Funktion_Build_List(int accuracy,String Digites) {	//Will make a txt-File with all the Points of the Sigmoid Function
		SigmoidPoints = new double[accuracy][2];
		double step = 20.0/accuracy;													//The sigmoidfunction will be split into 20.0/accuracy parts. It is more efficient than calculating all points
		DecimalFormat f = new DecimalFormat("#" + Digites);
		File SigmoidPointss = new File("Documents\\SigmoidPoints.txt");
		
		PrintWriter pWriter = null;
		try {
			pWriter = new PrintWriter(new BufferedWriter(new FileWriter(SigmoidPointss)));
		} catch (IOException e) {
			System.out.println("Die Datei" + SigmoidPointss.getName() +" konnte nicht gefunden werden");
			e.printStackTrace();
		}
		for(double x= -10.0;x<10;x+=step) {
			pWriter.print(f.format(1/(1+Math.exp(-x))));
			pWriter.println("//" + f.format(x));
		}
		pWriter.close();
		
		double x=-10.0;
		for(int i= 0;i<accuracy;i++) {
			SigmoidPoints[i][0] = x;SigmoidPoints[i][1] = 1/(1+Math.exp(-x));
			x+=step;
		}
	}
	
	
	//All Vector or Matrix Calculations
	protected static double[][] MatrixSubtraktion(double[][] a,double[][] b){
		double[][] c = new double[a.length][b[0].length];
		for(int n=0;n<c.length;n++) {
			for(int m=0;m<c[0].length;m++) {
					c[n][m] = a[n][m]-b[n][m];
			}
		}
		return c;
	}
	
	protected static double[][] MatrixAddition(double[][] a,double[][] b){
		double[][] c = new double[a.length][b[0].length];
		for(int n=0;n<c.length;n++) {
			for(int m=0;m<c[0].length;m++) {
					c[n][m] = a[n][m]+b[n][m];
			}
		}
		return c;
	}
	
	protected static double[][] Matrixmultiplication(double[] a,double[] b,double alpha){
		double[][] c = new double[a.length][b.length];
		for(int m=0;m<c[0].length;m++) {
			for(int n=0;n<c.length;n++) {
					c[n][m] = alpha * a[n]*b[m];
			}
		}
		return c;
	}
	
	public static double[] MatrixVectorMultiplication(double[][] a,double[] b){
		double[] c = new double[a.length];
		for(int i=0;i<c.length;i++) {
				for(int h=0;h<b.length;h++) {
					c[i] += a[i][h]*b[h];
			}
		}
		return c;
	}
	
	public static double[] MatrixAMultiplikation(double[][] a,double b){
		double[] c = new double[a.length];
		for(int i=0;i<c.length;i++) {
				for(int h=0;h<a[0].length;h++) {
					c[i] += a[i][h]*b;
			}
		}
		return c;
	}
	
	protected static double[] VektorAddition(double[] a,double[] b){
		double[] c = new double[a.length];
		for(int i=0;i<c.length;i++) {
				c[i] = a[i]+b[i];
		}
		return c;
	}
	
	protected static double[] VectorSubtraction(double[] a,double[] b){
		double[] c = new double[a.length];
		for(int i=0;i<c.length;i++) {
				c[i] = a[i]-b[i];
		}
		return c;
	}
	
	protected static double[] VectorElementMultiplication(double[] a,double[] b){
		double[] c = new double[a.length];
		for(int i=0;i<c.length;i++) {
				c[i] = a[i]*b[i];
		}
		
		return c;
	}
	
	protected static double[] VektorAMultiplikation(double a,double[] b){
		double[] c = new double[b.length];
		for(int i=0;i<c.length;i++) {
				c[i] = a*b[i];
		}
		
		return c;
	}
	
	protected static double[] Y_Output(double[] sum) {
		double[] Y = new double[sum.length];
		Y = SigmoidInsort(sum);
		return Y;
	}
	
	//Insort all Values of x into the List of Sigmoid Points
	private static double[] SigmoidInsort(double[] x) {
		double[] Psi = new double[x.length] ;
		
		for(int i=0;i<x.length;i++) {
			for(int a=0;a<SigmoidPoints.length;a++) {
				if(SigmoidPoints[a][0] >= x[i] && x[i] > -10.0 && x[i] < 10) {
					Psi[i] = SigmoidPoints[a-1][1];
					a=SigmoidPoints.length;
				}else if(x[i] <= -10.0 || x[i] >= 10){
					if(x[i] <= -10.0) {
						Psi[i] = 0.0;
					}else {
						Psi[i] = 1.0;
					}
					a=SigmoidPoints.length;
				}
			}
		}
		return Psi;
	}
}

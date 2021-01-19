import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Arrays;

/*
	This class represents one Layer of the neural Network. Each Layer has an Input and an Output. Also a Matrix of all Weights(=W)
	and the Calculations of the Changes.
*/

public class Layer extends Berechnungen{

	public double[][] W;
	private double[] sum;
	public double[] x;
	
	public double[] Y_Output;
	private double[] Y_Derivation_Output;
	private double[][] Weightchange;
	public double[] DeltaV;
	public String Name = null;
	
	public Layer(int NumberOfb,int NumberOfx,String Name){
		this.Name  = Name;
		InitialW(NumberOfb,NumberOfx+1);
		Weightchange = new double[W.length][W[0].length];
	}
	
	public void SetStartx(double[] x) {
		double[] StartValue = new double[x.length+1];
		StartValue[0] = 1.0;										//Initialising the Bias
		for(int i=0;i<x.length;i++) {
			StartValue[i+1] = x[i];
		}
		this.x = StartValue;
	}
	
	private void InitialW(int x,int y) {
		W = new double[x][y];
		for(int i=0;i<W.length;i++) {
			for(int j=0;j<W[0].length;j++) {
				W[i][j] = 1.0;
			}
		}
	} 	 	
	
	private void Ableitung(){
		double[] OneVector = new double[Y_Output.length];
		for(int i=0;i<Y_Output.length;i++) {
			OneVector[i] = 1.0;
		}
		Y_Derivation_Output = VectorElementMultiplication(Y_Output,VectorSubtraction(OneVector,Y_Output));
	}
	
	public void Delta(double[] DeltaThePre_Layer,double[][] Weightsoftheprelayer) {
		Ableitung();
		if(DeltaV == null) {
			DeltaV = new double[Y_Derivation_Output.length];
			Arrays.fill(DeltaV,0); 
		}
		
		for(int m=0;m<Y_Derivation_Output.length;m++) {
			double sum = 0;
			for(int n=0;n<DeltaThePre_Layer.length;n++) {
				sum += Weightsoftheprelayer[n][m] * DeltaThePre_Layer[n];
			}
			DeltaV[m] += Y_Derivation_Output[m] * sum;
		}
	}
	
	public void CommulativeWeightChange(double alpha,double[] Y_Der_Vorschicht) {
		
		if(Weightchange != null) {
			Weightchange = MatrixAddition(Matrixmultiplication(DeltaV,Y_Der_Vorschicht,alpha),Weightchange);
		}else {
			Weightchange = Matrixmultiplication(DeltaV,Y_Der_Vorschicht,alpha);
		}
		
	}
	
	public void AddingtheAvarage(int Numberoftrainingsamples) {
		double a = 1.0/Numberoftrainingsamples;
		MatrixAMultiplikation(Weightchange,a);
		W = MatrixSubtraktion(W,Weightchange);
		
	}

	public double[] Activation() {
		sum = MatrixVectorMultiplication(W, x);
		Y_Output = Y_Output(sum);
		return Y_Output;
	}

	public void getx() {
		System.out.println(x[1]);
	}
	
	public void SafeFiles(String Digites) {
		DecimalFormat f = new DecimalFormat("#" + Digites);
		File Gewichte = new File("Documents\\Gewichte"+Name+".txt");
		PrintWriter pWriter = null;
		try {
			pWriter = new PrintWriter(new BufferedWriter(new FileWriter(Gewichte)));
		} catch (IOException e) {
			System.out.println("Die Datei" + Gewichte.getName() +" konnte nicht gefunden werden");
			e.printStackTrace();
		}
		for(int n=0;n<W.length;n++) {
			for(int m=0;m<W[0].length;m++) {
				pWriter.print(f.format(W[n][m]).replace(',', '.') + " ");
			}
			pWriter.println();
		}
		pWriter.close();
	}

}

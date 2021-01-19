import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

/*
	This Programm should simulate a neural Network based on the Calculations from:
	https://lmb.informatik.uni-freiburg.de/lectures/old_lmb/mustererkennung/WS0506/08_b_ME.pdf
	
	The Goal of that Network is to predict or to recognise the handwritten Number.
	Your input is a GrayValueMatrix of one Picture(corresponds to x);
	The Trainingdata are about 20 Pictures of ones and twos (The goal is to use the Iris Data Set or the MNIST database)
	The NumberOfRuns,Learningrate and NumberOfNeuronsPerLayer are variable. They influence the accuracy of the Network
	A one dimensional Vector is the output. Each number is between 0 and 1. The number represent a Possibility. For Example:
	(1.0,0.0) = 1 or (0.13,0.87) is probably a 2
	If you want to test the Network with the same Weights they will be read from a txt file.
	
	This Programm is an Beta. It have some Problems. But i am still working on it and could need some Help.
*/

public class main extends Berechnungen{
	
	private static LinkedList<Layer> NN;														//The Neuronal Network is a LinkedList of Layer Objects
	
	public static void main(String[] args) {
		PictureAnalyse Pictures = new PictureAnalyse("TestBilder");								//Pictures is an Object for all training pictures
		PictureAnalyse TestPictures = new PictureAnalyse("Bilder");								//TestPictures is an Object for all test pictures
		
		Pictures.Analyse();																		//Transforming all Pictures in Arrays with their
		TestPictures.Analyse();																	//Gray values											
		
		Scanner sc = new Scanner(System.in);
		NN = new LinkedList<Layer>();
		
		double Learningrate = 0.2;																//Parameter of the Network()
		int NumberOfRuns = 10000;
		double[] x = Pictures.Grayvalues[0];
		double[][] Trainingdata = TestPictures.Grayvalues;
		double[][] TargetOutput = TestPictures.fileVektorNumber;
		int[] NumberOfNeuronsPerLayer = {Trainingdata[0].length,16,16,TargetOutput[0].length};
		
		/*int[] NumberOfNeuronsPerLayer = {2,4,4,2};											//Testing Arrays
		double[] x = {1.0,0.0};
		double[][] Trainingsdaten = {{0.0,1.0},
									 {1.0,0.0}};
		double[][] Sollausgabe = {{1.0,0.0},
								  {1.0,0.0}};*/
		
		InitialLayers(NN,NumberOfNeuronsPerLayer);												//Initialization of the Network: Set all the weights of all Layers on the value 1
		
		System.out.println("Should the weights be recalculated? y/n");							//All Weights are saved in txt Files. 
		String UserInput = sc.next();															// If you want to check a new input but didnt want to train the System again you can say n and the Weights will be read from the files
		if(UserInput.equals("y")) {
			System.out.println("Weights will be recalculated ...");
			for(int i=0;i<NumberOfRuns;i++) {
				for(int j=0;j<Trainingdata.length;j++) {
					Weightadjustment(Learningrate,Trainingdata[j],TargetOutput[j]);				//Calculation of all DeltaValues over all Trainingexamples
				}
				for(int h=0;h<NN.size();h++) {
					NN.get(h).AddingtheAvarage(Trainingdata.length);							//Adding the Avarage of the DeltaValues to the Weights
				}
			}
			WeightSafen();																		//Safing all the Weights in txt Files
		}else {
			WeightsReadAndTransfer();															//Reading the Weights of the txt Files
		}
		
		TestingTheNetwork(x,"0.00000");															//Output of the Network when x is the input. The string is the format of the output
			
		
	}
	
	//Writting the Output of the Network for x into the console
	private static void TestingTheNetwork(double[] x, String Decimalplaces) {
		double[] Output = StartNeuronalesNetz(x);
		DecimalFormat f = new DecimalFormat("#" + Decimalplaces );	
		for(int i=0;i<Output.length;i++) {
			System.out.println(f.format(Output[i])+ " ");
		}
		
	}
	
	//Adding Objects of Type Layer to the LinkedList which represent the Network
	private static void InitialLayers(LinkedList<Layer> NN,int[] NumberOfNeuronsPerLayer) {
		for(int i=1;i<NumberOfNeuronsPerLayer.length;i++) {
			NN.add(new Layer(NumberOfNeuronsPerLayer[i],NumberOfNeuronsPerLayer[i-1],"" + i));	//The input for each Layer is the Number of Neurons in that specific Layer and the Number of Neurons in the previous Layer
		}
	}
	
	//Forwardpass through the Network
	private static double[] StartNeuronalesNetz(double[] Start) {
		NN.getFirst().SetStartx(Start);
		for(int i=1;i<NN.size();i++) {
			NN.get(i).SetStartx(NN.get(i-1).Activation());
		}
		return NN.getLast().Activation();
	}
	
	//Reading the Weights from the txt file
	private static void WeightsReadAndTransfer() {
		for(int i=0;i<NN.size();i++) {
			File file = new File("Documents\\Gewichte"+(i+1)+".txt");							//Every Weight becomes his own file
			try {
				@SuppressWarnings("resource")
				BufferedReader in = new BufferedReader(new FileReader(file));
				String zeile = null;
				ArrayList<String[]> ListAllerZeilenDerDatei = new ArrayList<String[]>();
				while ((zeile = in.readLine()) != null) {
					ListAllerZeilenDerDatei.add(zeile.split(" "));
				}
				double[][] Weights = new double[ListAllerZeilenDerDatei.size()][ListAllerZeilenDerDatei.get(0).length];
				for(int n=0;n<ListAllerZeilenDerDatei.size();n++) {								//Converting all string Values in double
					String[] a = ListAllerZeilenDerDatei.get(n);
					for(int m=0;m<a.length;m++) {
						Weights[n][m] = Double.parseDouble(a[m]);
					}
				}
				NN.get(i).W = Weights;												
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	//Saving the Weights in txt Files
	private static void WeightSafen() {
		for(int i=0;i<NN.size();i++) {
			NN.get(i).SafeFiles("0.0000");
		}
	}

	//Backwordpass through the Network
	private static void Weightadjustment(double Learningrate,double[] x,double[] TargetOutput) {
		double[] Y = StartNeuronalesNetz(x);
		double[] OneVector = new double[Y.length];
		for(int i=0;i<Y.length;i++) {
			OneVector[i] = 1.0;																
		}
		double[] Y_deviation = VectorSubtraction(Y,TargetOutput);								//Calculate the deviation
		double[] Delta_of_the_Last_Layer = VectorElementMultiplication(Y_deviation,VectorElementMultiplication(NN.getLast().Y_Output,VectorSubtraction(OneVector,NN.getLast().Y_Output))); //Calculating the Delta of the last Layer
		
		NN.getLast().DeltaV = Delta_of_the_Last_Layer;
		for(int r=NN.size()-1;r>0;r--) {
			NN.get(r-1).Delta(NN.get(r).DeltaV,NN.get(r).W);									//Calculating all Deltas backwords
		}
		
		for(int h=0;h<NN.size();h++) {
				NN.get(h).CommulativeWeightChange(Learningrate,NN.get(h).x);					//Adding all Weight Changes to build an Average over all Training Examples
		}
	}

}


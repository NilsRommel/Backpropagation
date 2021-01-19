import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import javax.imageio.ImageIO;

/*
	Scanning all Pictures and making a GrayValueMatrix
*/

public class PictureAnalyse {
	static int Height = 25;
	static int Wide = 25;
	public double[][] Grayvalues;
	public static int[] fileNumber;
	public double[][] fileVektorNumber;
	String FilePath;
	
	public PictureAnalyse(String NameDesOrdners) {
		this.FilePath = NameDesOrdners;
	}
	
	public void Analyse() { 
			int AnzahlderVerschiedenenZahlen = 2;
			File Bilder = new File(FilePath);
			File[] fileArray = Bilder.listFiles();
			Grayvalues = new double[fileArray.length][Wide*Height];
			fileNumber = new int[fileArray.length];
			fileVektorNumber = new double[fileArray.length][AnzahlderVerschiedenenZahlen];
			
			DecimalFormat f = new DecimalFormat("#0.00");
			PrintWriter pWriter = null;
			
			
			for(int i=0;i<fileArray.length;i++) {
				BufferedImage img = null;
				try {
					img = ImageIO.read(fileArray[i]);
		 		} catch (IOException e) {
					e.printStackTrace();
				}
				int j = 0;
				for(int x=0;x<Height;x++) {
					for(int y=0;y<Wide;y++) {
						Color c = new Color(img.getRGB(x,y));
						Grayvalues[i][j] = (0.299 * c.getRed() + 0.587 * c.getGreen() + 0.114 * c.getBlue())/255.0; 	//Calculating The GrayValues = 0.299 * Red + 0.587 * Green + 0.114 * Blue
						j++;
					}
				}
			}
			
			
			
			try {
				//Write all Values in txt Files
	            pWriter = new PrintWriter(new BufferedWriter(new FileWriter("E:\\Eclipse\\Neuronale Netzte\\BackPropagation\\Documents\\"+FilePath+".txt")));
	            System.out.println("Textdatei "+FilePath+" wurde erfolgreich erstellt");
	            for(int i=0;i<fileArray.length;i++) {
	            	int j = 0;
	            	for(int x=0;x<Height;x++) {
	            		for(int y=0;y<Wide;y++) {
	            			pWriter.print(f.format(Grayvalues[i][j]) + " ");
	            			j++;
	            		}
	            		pWriter.println();
	            	}
	            	fileNumber[i] = Integer.parseInt( String.valueOf(fileArray[i].getName().charAt(0)));
	            	if(fileNumber[i] == 1) {
	            		fileVektorNumber[i][0] = 1.0;fileVektorNumber[i][1] = 0.0;
	            	}else if(fileNumber[i] == 2) {
	            		fileVektorNumber[i][0] = 0.0;fileVektorNumber[i][1] = 1.0;
	            	}
	            	pWriter.println("-" + fileArray[i].getName().charAt(0));
	            }
	            pWriter.print(fileArray.length);
	        } catch (IOException ioe) {
	        	System.out.println("Textdatei konnte nicht erstellt werden");
	            ioe.printStackTrace();
	        } finally {
	            if (pWriter != null){
	                pWriter.flush();
	                pWriter.close();
	            }
	        }
			
		}
}

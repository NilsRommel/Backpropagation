public class MnistMatrix {

    private double [][] data;

    private int nRows;
    private int nCols;

    private int label;

    public MnistMatrix(int nRows, int nCols) {
        this.nRows = nRows;
        this.nCols = nCols;

        data = new double[nRows][nCols];
    }

    public double getValue(int r, int c) {
        return data[r][c];
    }
    
    public double[] getMatrixAsVector() {
    	double[] MatrixAsVector = new double[data.length*data[0].length];
    	int a=0;
    	for(int i=0;i<data.length;i++) {
    		for(int j=0;j<data[0].length;j++) {
    			MatrixAsVector[a] = data[i][j];
    			a++;
    		}
    	}
        return MatrixAsVector;
    }

    public void setValue(int row, int col, int value) {
        data[row][col] = value/250.0;
    }

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    public int getNumberOfRows() {
        return nRows;
    }

    public int getNumberOfColumns() {
        return nCols;
    }

	public double[] getLableAsVector() {
		double[] LableAsVector = {0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
		switch(label) {
		case 0:
			LableAsVector[0] = 1.0;
			break;
		case 1:
			LableAsVector[1] = 1.0;
			break;
		case 2:
			LableAsVector[2] = 1.0;
			break;
		case 3:
			LableAsVector[3] = 1.0;
			break;
		case 4:
			LableAsVector[4] = 1.0;
			break;
		case 5:
			LableAsVector[5] = 1.0;
			break;
		case 6:
			LableAsVector[6] = 1.0;
			break;
		case 7:
			LableAsVector[7] = 1.0;
			break;
		case 8:
			LableAsVector[8] = 1.0;
			break;
		case 9:
			LableAsVector[9] = 1.0;
			break;
		}
		return LableAsVector;
		
	}

}
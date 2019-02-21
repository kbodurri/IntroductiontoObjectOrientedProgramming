/* ComplexMatrix.java
 *
 * A class that describes basic operations on complex number matrices.
 * Operations such as add, subtract, multiply of two complex matrices.
 * Also supports subMatrix which deletes one row and one column from the
 * complex matrix and finally calculation of determinat of the complex matrix.
 *
 * Contributors : Klajdi Bodurri and Eirini Tsitsopoulou.
 *
*/

public class ComplexMatrix {
    
    // dimensions of the complexArray
    private int dimRow, dimCol;

    // complexArray is a an array that keeps the complex numbers. 
    private double [][] complexArray;

    // A constructor that creates and initializes a complex array
    ComplexMatrix(int rows, int cols, RandomGenerator rg) { 
        this(rows, cols);  

        int i,j;

        // initiliaze the complexArray
        for (i=0; i<rows; i++) {
            for (j=0; j<cols; j++) {
                complexArray[i][j] = rg.getDouble();
            }
        }
    }

    // A constructor that creates a complex array
    ComplexMatrix(int rows, int cols) {
        int i,j;

        dimRow = rows;
        dimCol = cols;

        complexArray = new double[rows][cols];
    }

    // A constructor that creates and copies a complexMatrix to another.
    ComplexMatrix(ComplexMatrix original) {
        this(original.getDimRow(), original.getDimCol());

        int i,j;

        // initiliaze the complexArray
        for (i=0; i<dimRow; i++) {
            for (j=0; j<dimCol; j++) {
                complexArray[i][j] = original.getElement(i,j);
            }
        }
    } 

    // Addition of complexArray and matrix. Returns a new ComplexMatrix object.
    public ComplexMatrix add(ComplexMatrix matrix) {
        ComplexMatrix resultComplexMatrix = new ComplexMatrix(matrix.getDimRow(), matrix.getDimCol());

        int i,j;

        // check if the addition between the 2 matrices can be done.
        if (dimRow == matrix.getDimRow() && dimCol == matrix.getDimCol()) {
            for (i=0; i<dimRow; i++) { // do the addition 
                for (j=0; j<dimCol; j++) {
                    resultComplexMatrix.setElement(i, j, complexArray[i][j] + matrix.getElement(i, j));
                }
            }
            return resultComplexMatrix;
        }

        return null;
    }

    // Subtraction of complexArray and matrix. Returns a new ComplexMatrix object.
    public ComplexMatrix subtrack(ComplexMatrix matrix) {
        ComplexMatrix resultComplexMatrix = new ComplexMatrix(matrix.getDimRow(), matrix.getDimCol());

        int i,j;

        // check if the subtraction between the 2 matrices can be done.
        if (dimRow == matrix.getDimRow() && dimCol == matrix.getDimCol()) {
            for (i=0; i<dimRow; i++) { // do the subtraction 
                for (j=0; j<dimCol; j++) {
                    resultComplexMatrix.setElement(i, j, complexArray[i][j] - matrix.getElement(i, j));
                }
            }
            return resultComplexMatrix;
        }

        return null;
    }

    // assign the matrix to the complexArray.
    public void assign(ComplexMatrix matrix) {
        double [][] newComplexArray = new double[matrix.getDimRow()][matrix.getDimCol()];
        int i,j;

        // copies the matrix into to newComplexArray
        for (i=0; i<matrix.getDimRow(); i++){
            for (j=0; j<matrix.getDimCol(); j++){
                newComplexArray[i][j] = matrix.getElement(i, j);
            }
        }

        // update the complexArray, carbage collector will delete the previous complexArray
        updateArray(newComplexArray, matrix.getDimRow(), matrix.getDimCol());
    }

    // Return the number of complexArray[rowIndex][colIndex]
    public double getElement(int rowIndex, int colIndex) {
        return complexArray[rowIndex][colIndex];
    }

    // Sets the complexArray[rowIndex][colIndex] to a number
    public void setElement(int rowIndex, int colIndex, double number) {
        complexArray[rowIndex][colIndex] = number;
    }

    // Returns the row dimension of the complex array
    public int getDimRow() {
        return dimRow;
    }

    // Returns the column dimension of the complex array
    public int getDimCol() {
        return dimCol;
    }

    // updates the complexArray and its dimensionality
    private void updateArray(double newComplexArray[][], int rows, int cols) {
        complexArray = newComplexArray;
        updateDimRow(rows);
        updateDimCol(cols);
    }

    // updates the row dimension of the complexArray
    private void updateDimRow(int rows) {
        dimRow = rows;
    }

    // updates the column dimension of the complexArray
    private void updateDimCol(int cols) {
        dimCol = cols;
    }

    @Override
    public String toString() {
        String arrayContents = "[";
        int i, j;
        
        /* iterate through complexArray and convert the contents of the array to
        * a string.
        */
        for (i=0; i<dimRow; i++){
            for(j=0; j<dimCol; j++){
                arrayContents = arrayContents + String.format("%.2f", complexArray[i][j]);

                /* add comma to the end of each complex number except for the 
                * last one.
                */
                if (j != dimCol - 1){
                    arrayContents = arrayContents + ", ";
                }
            }
            arrayContents = arrayContents + ";\n";
        }

        //  trim the last \n and add the character ']'
        arrayContents = arrayContents.trim() + "]";

        return arrayContents;
    }

    // HERE YOU CAN DO YOUR TESTS OF THE CLASS
    public static void main(String[] args) {
        RandomGenerator rg = new RandomGenerator(6);
        ComplexMatrix complexMatrixObject1 = new ComplexMatrix(5, 5, rg);
        System.out.println(complexMatrixObject1.toString());
        
        ComplexMatrix complexMatrixObject2 = new ComplexMatrix(2, 2, rg);
        
        complexMatrixObject1.assign(complexMatrixObject2);
        
        System.out.println(complexMatrixObject1.toString());
    }
}

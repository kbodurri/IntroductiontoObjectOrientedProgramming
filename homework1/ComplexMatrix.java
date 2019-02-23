/* ComplexMatrix.java
 *
 * A class that describes basic operations on complex number matrices.
 * Operations such as add, subtract, multiply of two complex matrices.
 * Also supports subMatrix which deletes one row and one column from the
 * complex matrix and finally calculation of determinant of the complex matrix.
 *
 * Contributors : Klajdi Bodurri and Eirini Tsitsopoulou.
 *
*/

import java.lang.Math;

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
        // check if the addition cannot be done
        if (dimRow != matrix.getDimRow() || dimCol != matrix.getDimCol()){
            return null;
        } 

        ComplexMatrix resultComplexMatrix = new ComplexMatrix(matrix.getDimRow(), matrix.getDimCol());

        int i,j;

        // check if the addition between the 2 matrices can be done.
        for (i=0; i<dimRow; i++) { // do the addition 
            for (j=0; j<dimCol; j++) {
                resultComplexMatrix.setElement(i, j, complexArray[i][j] + matrix.getElement(i, j));
            }
        }
        return resultComplexMatrix;
    }

    // Subtraction of complexArray and matrix. Returns a new ComplexMatrix object.
    public ComplexMatrix subtrack(ComplexMatrix matrix) {
        // check if the subtraction cannot be done
        if (dimRow != matrix.getDimRow() || dimCol != matrix.getDimCol()){
            return null;
        } 

        ComplexMatrix resultComplexMatrix = new ComplexMatrix(matrix.getDimRow(), matrix.getDimCol());

        int i,j;

        for (i=0; i<dimRow; i++) { // do the subtraction 
            for (j=0; j<dimCol; j++) {
                resultComplexMatrix.setElement(i, j, complexArray[i][j] - matrix.getElement(i, j));
            }
        }
        return resultComplexMatrix;
    }

    // Multiplication of complexArray and matrix.
    public ComplexMatrix multiply(ComplexMatrix matrix) {
        // check if multiplication of two matrices cannot be done.
        if (dimCol != matrix.getDimRow()) {
            return null;
        }

        ComplexMatrix resultComplexMatrix = new ComplexMatrix(dimRow, matrix.getDimCol());
        int i,j,k;
        double tmp_number, product;

        for (i=0; i<dimRow; i++){ // rows of complexArray
            for (j=0; j<matrix.getDimCol(); j++) { // columns of matrix
                resultComplexMatrix.setElement(i,j, 0.0);
                for (k=0; k<dimCol; k++){
                    // C[i][j] += A[i][k]*B[k][j]
                    tmp_number = resultComplexMatrix.getElement(i, j);
                    product = complexArray[i][k]*matrix.getElement(k,j);
                    resultComplexMatrix.setElement(i,j, tmp_number+product);
                }
            } 
        }
        return resultComplexMatrix;
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

    public ComplexMatrix subMatrix(int delRow, int delCol) {
        // check first if subMatrix is not allowed
        if (delRow < 0 || delRow >= dimRow || delCol < 0 || delCol >= dimCol){
            return null;
        }

        ComplexMatrix trimmedMatrix = new ComplexMatrix(dimRow-1, dimCol-1);

        int i, j, rowIndex=0, colIndex=0;
        
        /* rowIndex and colIndex gives the index of trimmedArray in which each 
         * element of the complexArray should be stored.
        */ 
        for (i=0; i<dimRow; i++) {
            
            colIndex = 0;
            
            if (i == delRow) { // skip this row
                continue;
            }

            for (j=0; j<dimCol; j++) {
                if (j == delCol) { // skip this column
                    continue;
                }

                // copy the elements to the new matrix
                trimmedMatrix.setElement(rowIndex, colIndex, complexArray[i][j]);
                colIndex++;  
            }

            rowIndex++;
        }
        
        return trimmedMatrix;
    }

    public double determinant() {
        // if the complexArray is not squared.
        if (dimRow != dimCol) {
            return -0.0001; // WE SHOULD CHANGE IT TO NULL!!!
        }

        ComplexMatrix tmpMatrix;
        double result=0.0;
        int i,j;

        if (dimRow == 2) { // determinant of a 2x2 array.
            // a*d - c*b
            result = (complexArray[0][0]*complexArray[1][1]) - complexArray[0][1]*
                complexArray[1][0];
        }
        else { // calculate determinant recursively
            for (i=0; i<dimRow; i++) {
                tmpMatrix = subMatrix(0,i);
                result = result + ((Math.pow(-1, i)*complexArray[0][i])*tmpMatrix.determinant());
            }
        }

        return result;
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
        ComplexMatrix complexMatrixObject1 = new ComplexMatrix(4, 4, rg);
        System.out.println(complexMatrixObject1.toString());
        System.out.println(complexMatrixObject1.determinant());

  }
}

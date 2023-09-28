package fifteenpuzzle;

import java.util.Arrays;

public class IntMatrixWrapper {
    private byte[][] matrix;

    public IntMatrixWrapper(byte[][] matrix){
        this.matrix = matrix;
    }

    public byte[][] getMatrix(){
        return matrix;
    }

    @Override
    public int hashCode(){
        return Arrays.deepHashCode(matrix);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof IntMatrixWrapper)) {
            return false;
        }
        IntMatrixWrapper other = (IntMatrixWrapper) obj;
        return Arrays.deepEquals(matrix, other.matrix);
    }

}

package matrix;
import java.util.Arrays;
import java.util.Random;

public class UsualMatrix {
    private final double[][] data;

    public UsualMatrix(int rows, int columns) {
        this.data = new double[rows][columns];
    }

    public UsualMatrix(double[][] data) {
        this.data = data;
    }

    public int getRows() {
        return data.length;
    }

    public int getColumns() {
        return data[0].length;
    }

    public double get(int row, int col) {
        return data[row][col];
    }

    public void set(int row, int col, double value) {
        data[row][col] = value;
    }


    public UsualMatrix multiply(UsualMatrix other) {
        if (getColumns() != other.getRows()) {
            throw new IllegalArgumentException("Matrices dimensions are not compatible for multiplication");
        }

        int rows = getRows();
        int columns = other.getColumns();
        int commonDimension = getColumns();
        UsualMatrix result = new UsualMatrix(rows, columns);
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                double sum = 0;
                for (int k = 0; k < commonDimension; k++) {
                    sum += get(row, k) * other.get(k, col);
                }
                result.set(row, col, sum);
            }
        }
        return result;
    }

    public static UsualMatrix random(int rows, int columns, Random random) {
        double[][] data = new double[rows][columns];
        for (double[] row : data) {
            Arrays.setAll(row, i -> random.nextDouble());
        }
        return new UsualMatrix(data);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof UsualMatrix)) {
            return false;
        }
        UsualMatrix other = (UsualMatrix) obj;
        if (getRows() != other.getRows() || getColumns() != other.getColumns()) {
            return false;
        }
        for (int row = 0; row < getRows(); row++) {
            for (int col = 0; col < getColumns(); col++) {
                if (get(row, col) != other.get(row, col)) {
                    return false;
                }
            }
        }
        return true;
    }
}

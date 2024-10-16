package matrix;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MatrixVectorProduct {
    private final int numThreads;

    public MatrixVectorProduct(int numThreads) {
        this.numThreads = numThreads;
    }

    public double[] multiply(double[] vector, UsualMatrix matrix) {
        int numRows = matrix.getRows();
        int numCols = matrix.getColumns();
        double[] result = new double[numRows];

        if (numThreads == 1) {
            for (int i = 0; i < numRows; i++) {
                for (int j = 0; j < numCols; j++) {
                    result[i] += matrix.get(i, j) * vector[j];
                }
            }
        } else {
            ExecutorService executor = Executors.newFixedThreadPool(numThreads);
            for (int i = 0; i < numRows; i++) {
                int row = i;
                executor.submit(() -> {
                    for (int j = 0; j < numCols; j++) {
                        result[row] += matrix.get(row, j) * vector[j];
                    }
                });
            }
            executor.shutdown();
            try {
                executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}
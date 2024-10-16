package matrix;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ParallelMatrixProduct {
    private final int numThreads;
    private final ExecutorService executor;

    public ParallelMatrixProduct(int numThreads) {
        this.numThreads = numThreads;
        this.executor = Executors.newFixedThreadPool(numThreads);
    }

    public UsualMatrix multiply(UsualMatrix a, UsualMatrix b) {
        if (a.getColumns() != b.getRows()) {
            throw new IllegalArgumentException("Matrices dimensions are not compatible for multiplication");
        }

        int rows = a.getRows();
        int columns = b.getColumns();
        UsualMatrix result = new UsualMatrix(rows, columns);

        int chunkSize = rows / numThreads;
        for (int i = 0; i < numThreads; i++) {
            int startRow = i * chunkSize;
            int endRow = (i == numThreads - 1) ? rows : (i + 1) * chunkSize;

            executor.submit(() -> {
                for (int row = startRow; row < endRow; row++) {
                    for (int col = 0; col < columns; col++) {
                        double sum = 0;
                        for (int k = 0; k < a.getColumns(); k++) {
                            sum += a.get(row, k) * b.get(k, col);
                        }
                        result.set(row, col, sum);
                    }
                }
            });
        }

        try {
            executor.shutdown();
            executor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result;
    }
}
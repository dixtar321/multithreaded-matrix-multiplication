import matrix.*;
import java.util.Random;

public class Main {
public static void main(String[] args) {
    Random random = new Random();
    UsualMatrix a = UsualMatrix.random(1000, 1000, random);
    UsualMatrix b = UsualMatrix.random(1000, 1000, random);

    long start = System.currentTimeMillis();
    UsualMatrix c = a.multiply(b);
    long end = System.currentTimeMillis();
    System.out.println("Sequential multiplication took " + (end - start) + "ms");

    ParallelMatrixProduct parallelMultiplier = new ParallelMatrixProduct(8);
    start = System.currentTimeMillis();
    UsualMatrix d = parallelMultiplier.multiply(a, b);
    end = System.currentTimeMillis();
    System.out.println("Parallel multiplication took " + (end - start) + "ms");

    System.out.println("Matrices are equal: " + c.equals(d));
}
}
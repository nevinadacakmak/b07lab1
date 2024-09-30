import java.io.File;
import java.io.IOException;

public class Driver {
    public static void main(String[] args) throws IOException {
        // Test default constructor
        Polynomial p = new Polynomial();
        System.out.println("p(3) = " + p.evaluate(3));

        // Test constructor with coefficients and exponents
        double[] c1 = {6, 5};
        int[] e1 = {0, 3};
        Polynomial p1 = new Polynomial(c1, e1);
        System.out.println("p1(3) = " + p1.evaluate(3));

        double[] c2 = {-2, -9};
        int[] e2 = {1, 4};
        Polynomial p2 = new Polynomial(c2, e2);
        System.out.println("p2(3) = " + p2.evaluate(3));

        // Test addition of polynomials
        Polynomial s = p1.add(p2);
        System.out.println("s(0.1) = " + s.evaluate(0.1));

        // Test root checking
        if (s.hasRoot(1)) {
            System.out.println("1 is a root of s");
        } else {
            System.out.println("1 is not a root of s");
        }

        // Test multiplication of polynomials
        Polynomial m = p1.multiply(p2);
        System.out.println("m(0.1) = " + m.evaluate(0.1));

        // Test reading polynomial from a file
        Polynomial f = new Polynomial(new File("./file.txt"));
        System.out.println("f(0.1) = " + f.evaluate(0.1));

        // Test adding two polynomials and saving to file
        Polynomial f2 = f.add(m);
        f2.saveToFile("file2.txt");
        System.out.println("f2(0.1) = " + f2.evaluate(0.1));

        // Test additional polynomial cases and assertions
        Polynomial p0 = new Polynomial();
        Polynomial p3 = p1.add(p2);
        Polynomial p4 = p2.add(p3);
        Polynomial p5 = p2.multiply(p3);

        System.out.println("Assert check for p5(2) == 4598532");
        assert p5.evaluate(2) == 4598532;

        System.out.println("Assert checks for zero polynomials");
        assert p1.multiply(p0).evaluate(3) == 0;
        assert p0.multiply(p1).evaluate(0.5) == 0;

        System.out.println("Assert checks for polynomial evaluations");
        assert p1.evaluate(2) == 64;
        assert p1.add(p0).evaluate(2) == 64;
        assert p0.add(p1).evaluate(2) == 64;

        System.out.println("All tests passed!");
    }
}

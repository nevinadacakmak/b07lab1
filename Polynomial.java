import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class Polynomial {
    double[] coefficients;
    int[] exponents;

    // Constructor from file
    public Polynomial(File file) {
        try {
            Scanner scanner = new Scanner(file);
            String line = scanner.nextLine();
            scanner.close();

            // Remove all whitespaces and handle + and - signs 
            line = line.replaceAll("\\s+", "");
            String[] terms = line.split("(?=[+-])");

            coefficients = new double[terms.length];
            exponents = new int[terms.length];

            for (int i = 0; i < terms.length; i++) 
            {
                String term = terms[i];
                if (term.contains("x")) 
                {
                    String[] parts = term.split("x");
                    coefficients[i] = parts[0].isEmpty() || parts[0].equals("+") ? 1 : (parts[0].equals("-") ? -1 : Double.parseDouble(parts[0]));
                    if (parts.length > 1 && parts[1].contains("^")) 
                    {
                        exponents[i] = Integer.parseInt(parts[1].substring(1));
                    } 
                    else 
                    {
                        exponents[i] = 1;
                    }
                } 
                else 
                {
                    coefficients[i] = Double.parseDouble(term);
                    exponents[i] = 0;  // Constant term
                }
            }
        } 
        catch (FileNotFoundException e) 
        {
            System.out.println("File not found");
        } 
        catch (NumberFormatException e) 
        {
            System.out.println("Invalid Number");
        }
    }

    public Polynomial() {
        coefficients = new double[] {0};
        exponents = new int[] {0};
    }

    public Polynomial(double[] coeffs, int[] exps) {
        this.coefficients = coeffs;
        this.exponents = exps;
    }

    public Polynomial add(Polynomial other) {
        Polynomial result = new Polynomial();
        result.coefficients = new double[coefficients.length + other.coefficients.length];
        result.exponents = new int[coefficients.length + other.exponents.length];

        int index = 0;
        for (int i = 0; i < coefficients.length; i++) 
        {
            result.coefficients[index] = coefficients[i];
            result.exponents[index] = exponents[i];
            index++;
        }

        for (int i = 0; i < other.coefficients.length; i++) 
        {
            result = addTerm(result, other.coefficients[i], other.exponents[i]);
        }

        result = simplify(result);
        return result;
    }

    private Polynomial addTerm(Polynomial poly, double newCoeff, int newExp) {
        for (int i = 0; i < poly.exponents.length; i++) 
        {
            if (poly.exponents[i] == newExp) 
            {
                poly.coefficients[i] += newCoeff;
                return poly;
            }
        }

        double[] newCoefficients = new double[poly.coefficients.length + 1];
        int[] newExponents = new int[poly.exponents.length + 1];

        for (int i = 0; i < poly.coefficients.length; i++)
        {
            newCoefficients[i] = poly.coefficients[i];
            newExponents[i] = poly.exponents[i];
        }

        newCoefficients[poly.coefficients.length] = newCoeff;
        newExponents[poly.exponents.length] = newExp;

        poly.coefficients = newCoefficients;
        poly.exponents = newExponents;

        return poly;
    }

    // Simplify the polynomial
    private Polynomial simplify(Polynomial poly) {
        int count = 0;
        for (double coef : poly.coefficients) 
        {
            if (coef != 0) count++;
        }

        double[] newCoefficients = new double[count];
        int[] newExponents = new int[count];
        int index = 0;

        for (int i = 0; i < poly.coefficients.length; i++) {
            if (poly.coefficients[i] != 0) 
            {
                newCoefficients[index] = poly.coefficients[i];
                newExponents[index] = poly.exponents[i];
                index++;
            }
        }

        poly.coefficients = newCoefficients;
        poly.exponents = newExponents;
        return poly;
    }

    public double evaluate(double x) {
        double result = 0;
        for (int i = 0; i < coefficients.length; i++) 
        {
            result += coefficients[i] * Math.pow(x, exponents[i]);
        }
        return result;
    }

    public boolean hasRoot(double x) {
        return evaluate(x) == 0;
    }

    public Polynomial multiply(Polynomial poly) {
        Polynomial result = new Polynomial();

        for (int i = 0; i < coefficients.length; i++) {
            for (int j = 0; j < poly.coefficients.length; j++) 
            {
                result = addTerm(result, coefficients[i] * poly.coefficients[j], exponents[i] + poly.exponents[j]);
            }
        }

        result = simplify(result);
        return result;
    }

    // Save polynomial to file
    public void saveToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(filename)) 
        {
            for (int i = 0; i < coefficients.length; i++) 
            {
                if (i > 0 && coefficients[i] > 0) 
                {
                    writer.print("+");
                }
                writer.print(coefficients[i]);
                if (exponents[i] != 0) 
                {
                    writer.print("x" + exponents[i]);
                }
            }
        } 
        catch (FileNotFoundException e) 
        {
            System.out.println("Error writing to file.");
        }
    }
}

/*
i. It has one field representing the coefficients of the polynomial using an array of
double. A polynomial is assumed to have the form 𝑎 ! + 𝑎" 𝑥 " + ⋯ + 𝑎 #$" 𝑥 #$" .
For example, the polynomial 6 − 2𝑥 + 5𝑥 % would be represented using the
array [6, -2, 0, 5]
ii. It has a no-argument constructor that sets the polynomial to zero (i.e. the
corresponding array would be [0])
iii. It has a constructor that takes an array of double as an argument and sets the
coefficients accordingly
iv. It has a method named add that takes one argument of type Polynomial and
returns the polynomial resulting from adding the calling object and the argument
v. It has a method named evaluate that takes one argument of type double
representing a value of x and evaluates the polynomial accordingly. For example,
if the polynomial is 6 − 2𝑥 + 5𝑥 % and evaluate(-1) is invoked, the result should
be 3.
vi. It has a method named hasRoot that takes one argument of type double and
determines whether this value is a root of the polynomial or not. Note that a root
is a value of x for which the polynomial evaluates to zero.
*/
public class Polynomial 
{
    double[] coefficients;

    // No-argument constructor
    public Polynomial() 
    {
        coefficients = new double[1];
        coefficients[0] = 0;
    }

    // Constructor
    public Polynomial(double[] coeffs) 
    {
        coefficients = new double[coeffs.length];
        for (int i = 0; i < coeffs.length; i++) 
        {
            coefficients[i] = coeffs[i];
        }
    }

    public Polynomial add(Polynomial other) 
    {
        int maxLength = Math.max(coefficients.length, other.coefficients.length);
        
        double[] result = new double[maxLength];

        for (int i = 0; i < maxLength; i++) 
        {
            double thisCoeff = i < coefficients.length ? coefficients[i] : 0;
            double otherCoeff = i < other.coefficients.length ? other.coefficients[i] : 0;
            result[i] = thisCoeff + otherCoeff;
        }

        return new Polynomial(result);
    }

    public double evaluate(double x) 
    {
        double result = 0;
        for (int i = 0; i < coefficients.length; i++) 
        {
            result += coefficients[i] * Math.pow(x, i);
        }
        return result;
    }

    public boolean hasRoot(double x) 
    {
        return evaluate(x) == 0;
    }
}
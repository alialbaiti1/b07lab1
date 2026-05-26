import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Polynomial {
    double[] coefficients;
    int[] exponents;

    public Polynomial() {
        coefficients = new double[0];
        exponents = new int[0];
    }

    public Polynomial(double[] coefficients, int[] exponents) {
        if(coefficients.length != exponents.length) {
            System.out.println("The number of coefficients is not equal to the number of exponents.");
            coefficients = new double[0];
            exponents = new int[0];
        }
        this.coefficients = new double[coefficients.length];
        this.exponents = new int[exponents.length];
        for(int i = 0; i < coefficients.length; i++) {
            this.coefficients[i] = coefficients[i];
            this.exponents[i] = exponents[i];
        }
        
        cleanPolynomial();
    }

    public Polynomial(File f) {
        Scanner scanner;
		try {
			scanner = new Scanner(f);
		} catch (FileNotFoundException e) {
			System.out.println("File not found. Setting to zeros.");
			coefficients = new double[0];
			exponents = new int[0];
			return;
		}
        String expression = scanner.nextLine();
        
        

        String[] elements = expression.split("[+-]");
        int[] signs = new int[elements.length];
        if(expression.charAt(0) == '-') {
            signs[0] = -1;
        }
        else {
            signs[0] = 1;
        }

        int counter = 1;
        for(int i = 1; i < expression.length(); i++) {
            if(expression.charAt(i) == '-') {
                signs[counter] = -1;
                counter++;
            }
            else if(expression.charAt(i) == '+') {
                signs[counter] = 1;
                counter++;
            }
        }

        coefficients = new double[elements.length];
        exponents = new int[elements.length];

        for(int i = 0; i < elements.length; i++) {
            String[] numerics = elements[i].split("x", -1);
            if(numerics.length == 1) {
                coefficients[i] = Double.parseDouble(numerics[0]) * signs[i];
                exponents[i] = 0;
                continue;
            }
            if(numerics[0] != "") {
                coefficients[i] = Double.parseDouble(numerics[0]) * signs[i];
            }
            else {
                coefficients[i] = signs[i];
            }
            if(numerics[1] != "") {
                exponents[i] = Integer.parseInt(numerics[1]);
            }
            else {
                exponents[i] = 1;
            }
        }

        cleanPolynomial();
    }

    private void cleanPolynomial() {
        // Adds any duplicate exponents to the first instance of the exponent and trims the arrays
        int size = 0;
        for(int i = 0; i < coefficients.length; i++) {
            if(coefficients[i] == 0) {
                continue;
            }
            for(int j = i+1; j < coefficients.length; j++) {
                if(exponents[j] == exponents[i])
                {
                    coefficients[i] += coefficients[j];
                    coefficients[j] = 0;
                    exponents[j] = 0;
                }
            }
            size++;
        }

        double[] newCoefficients = new double[size];
        int[] newExponents = new int[size];

        int i = 0;
        int j = 0;
        while(i < size) {
            if(coefficients[j] == 0) {
                j++;
                continue;
            }
            newCoefficients[i] = coefficients[j];
            newExponents[i] = exponents[j];
            i++;
            j++;
        }
        coefficients = newCoefficients;
        exponents = newExponents;
    }

    public Polynomial add(Polynomial otherPolynomial)
    {
        int maxLength = this.coefficients.length + otherPolynomial.coefficients.length;
        double[] newCoefficients = new double[maxLength];
        int[] newExponents = new int[maxLength];

        for(int i = 0; i < exponents.length; i++)
        {
            newExponents[i] = exponents[i];
            newCoefficients[i] = coefficients[i];
        }

        for(int i = 0; i < otherPolynomial.exponents.length; i++)
        {
            newExponents[i + coefficients.length] = otherPolynomial.exponents[i];
            newCoefficients[i + coefficients.length] = otherPolynomial.coefficients[i];
        }

        Polynomial newPolynomial = new Polynomial(newCoefficients, newExponents);
        newPolynomial.cleanPolynomial();

        return newPolynomial;
    }

    public double evaluate(double x) {
        double sum = 0;
        for(int i = 0; i < coefficients.length; i++) {
            sum += (coefficients[i] * Math.pow(x, exponents[i]));
        }
        return sum;
    }

    public boolean hasRoot(double x) {
        return (evaluate(x) == 0);
    }

    public Polynomial multiply(Polynomial otherPolynomial) {
        int size = this.coefficients.length * otherPolynomial.coefficients.length;
        double[] newCoefficients = new double[size];
        int[] newExponents = new int[size];
        
        for(int i = 0; i < coefficients.length; i++) {
            for(int j = 0; j < otherPolynomial.coefficients.length; j++) {
                newCoefficients[(i * otherPolynomial.coefficients.length) + j] = coefficients[i] * otherPolynomial.coefficients[j];
                newExponents[(i * otherPolynomial.coefficients.length) + j] = exponents[i] + otherPolynomial.exponents[j];
            }
        }

        Polynomial newPolynomial = new Polynomial(newCoefficients, newExponents);
        newPolynomial.cleanPolynomial();

        return newPolynomial;
    }

    public void writeToFile(String fileName) {
        FileWriter fwriter;
		try {
			fwriter = new FileWriter(fileName);
			fwriter.write(this.toString());
	        fwriter.close();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
    }

    @Override
    public String toString() {
        String result = "";
        for(int i = 0; i < coefficients.length; i++) {
            if(coefficients[i] != 0) {
                if(coefficients[i] > 0 && i != 0) {
                    result += "+";
                }
                if(coefficients[i] != 1) {
                	result += Double.toString(coefficients[i]);
                }
            }
            if(exponents[i] != 0) {
                result += "x";
                if(exponents[i] != 1) {
                	result += Integer.toString(exponents[i]);
                }
            }
        }
        return result;
    }
}

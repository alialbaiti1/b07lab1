public class Polynomial {
    double[] coefficients;

    public Polynomial() {
        coefficients = new double[1];
    }

    public Polynomial(double[] coefficients) {
        this.coefficients = new double[coefficients.length];
        for(int i = 0; i < coefficients.length; i++) {
            this.coefficients[i] = coefficients[i];
        }
    }

    public Polynomial add(Polynomial otherPolynomial)
    {
        int maxLength = otherPolynomial.coefficients.length;
        if(coefficients.length > maxLength) {
            maxLength = coefficients.length;
        }
        Polynomial resultPolynomial = new Polynomial(new double[maxLength]);

        for(int i = 0; i < maxLength; i++) {
            if(i < otherPolynomial.coefficients.length && i < coefficients.length){
                resultPolynomial.coefficients[i] = otherPolynomial.coefficients[i] + coefficients[i];
            }
            else if(i >= otherPolynomial.coefficients.length) {
                resultPolynomial.coefficients[i] = coefficients[i];
            }
            else {
                resultPolynomial.coefficients[i] = otherPolynomial.coefficients[i];
            }
        }

        return resultPolynomial;
    }

    public double evaluate(double x) {
        double sum = 0;
        for(int i = 0; i < coefficients.length; i++) {
            sum += (coefficients[i] * Math.pow(x, i));
        }
        return sum;
    }

    public boolean hasRoot(double x) {
        return (evaluate(x) == 0);
    }
}

import java.io.File;
import java.io.IOException;

public class Driver {
    public static void main(String [] args) throws IOException {
        Polynomial p = new Polynomial();
        System.out.println(p.evaluate(3));
        double [] c1 = {6,3,-1,5};
        int[] e1 = {1,0,5,3};
        Polynomial p1 = new Polynomial(c1, e1);
        double [] c2 = {1,-2,9,9,-9};
        int[] e2 = {2,4,4,6,8};
        Polynomial p2 = new Polynomial(c2, e2);
        System.out.println(p1);
        System.out.println(p2);
        Polynomial s = p1.add(p2);
        System.out.println(s);
        System.out.println("s(1) = " + s.evaluate(1));
        if(s.hasRoot(1))
            System.out.println("1 is a root of s");
        else
            System.out.println("1 is not a root of s");
        s.writeToFile("tst.txt");
        File f = new File("tst.txt");
        Polynomial n = new Polynomial(f);
        System.out.println(n);
        
        double[] c3 = {1, 4};
        int[] e3 = {0, 3};
        Polynomial m1 = new Polynomial(c3, e3);
        double[] c4 = {2, 3};
        int[] e4 = {0, 2};
        Polynomial m2 = new Polynomial(c4, e4);
        Polynomial r = m1.multiply(m2);
        System.out.println(r);
    }
}
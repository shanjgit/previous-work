package warmup;

import java.util.Set;
import java.util.HashSet;

public class Quadratic {

    /**
     * Find the integer roots of a quadratic equation, ax^2 + bx + c = 0.
     * @param a coefficient of x^2
     * @param b coefficient of x
     * @param c constant term.  Requires that a, b, and c are not ALL zero.
     * @return all integers x such that ax^2 + bx + c = 0.
     */
    public static Set<Integer> roots(int a, int b, int c) {
        assert a != 0 || b != 0 || c != 0;
        double num = Math.pow(b, 2.0) - 4.0 * a * c;
        if (num < 0 ||(a == 0 && b == 0 && c != 0)) return new HashSet<Integer>();
        else if (num < 0.0001 && num > -0.0001) {  
            Set<Integer> ans = new HashSet<Integer>();
            ans.add((int) (-b/(2.*a)));
            return ans;
            
        }
        else if (a == 0 && b != 0){
            Set<Integer> ans = new HashSet<Integer>();
            ans.add(-c/b);
            return ans;
        }

        double sqr = Math.sqrt(num);
        double higherAns =  (-b + sqr) / (2.*a);
        double lowerAns =   (-b - sqr) / (2.*a);
        if (higherAns != (int) higherAns || lowerAns != (int) lowerAns){return new HashSet<Integer>();}
        Set<Integer> ans = new HashSet<Integer>();
        ans.add( (int)higherAns);
        ans.add( (int)lowerAns);
        return ans;
    }

    
    /**
     * Main function of program.
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        System.out.println("For the equation x^2 - 4x + 4 = 0, the possible solutions are:");
        Set<Integer> result = roots(1, -4, 4);
        System.out.println(result);
    }

    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */
}

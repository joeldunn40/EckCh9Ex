import java.math.BigInteger;

/** Eck Exercise 9.1
 * Program to test Fibonacci and Factorial subroutines
 * Subroutines are recursive
 * @author Joel
 *
 */
public class FibonacciFactorialRecursive {

	public static void main(String[] args) {
		
		BigInteger V1, V2;
		
		for (int N = 0; N < 10; N++) {
			V1 = fibonacci(BigInteger.valueOf((long)N));
			V2 = factorial(BigInteger.valueOf((long)N));
			System.out.print("Fib("+N+") = " + V1);
			System.out.println("   Fac("+N+") = " + V2);
		} // end for N
		
	} // end main
	
	public static BigInteger fibonacci(BigInteger n) {
		if (n.signum() == -1)
			throw new IllegalArgumentException("Negative numbers not allowed");
		
		if (n.intValue()==0 || n.intValue()==1) {
			return BigInteger.ONE;
		} else {
			return fibonacci( n.subtract(BigInteger.ONE) ).add(fibonacci( n.subtract(BigInteger.ONE).subtract(BigInteger.ONE) ) );
		} 
	} // end fibonacci

	public static BigInteger factorial(BigInteger n) {
		if (n.signum() == -1)
			throw new IllegalArgumentException("Negative numbers not allowed");
		
		if (n.intValue()==0 ) {
			return BigInteger.ONE;
		} else {
			return n.multiply( factorial( n.subtract(BigInteger.ONE) ) );
		} 
	} // end factorial

}

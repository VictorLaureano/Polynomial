public interface Polynomial extends Iterable<Term> {

	public String getString();

	public int getSize();

	public Term getElement(int i );

	public Polynomial add(Polynomial P2);
	
	public Polynomial subtract(Polynomial P2);
	
	public Polynomial multiply(Polynomial P2);
	
	public Polynomial multiply(double c);

	public Polynomial derivative();
	
	public Polynomial indefiniteIntegral();
	
	public double definiteIntegral(double a, double b);
	
	public int degree();
	
	public double evaluate(double x);

	

	

}

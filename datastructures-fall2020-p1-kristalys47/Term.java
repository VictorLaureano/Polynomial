public interface Term {
	
	public double getCoefficient();
	
	public int getExponent();
	
	public double evaluate(double x);

	public void setCoefficient(double coefficient);

	public void setExponent(int exponent);

	
}

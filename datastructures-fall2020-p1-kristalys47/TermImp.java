public class TermImp implements Term{
    public double coefficient;
    public int exponent;

    public TermImp(double coefficient, int exponent){
        if(coefficient==0){
            throw new IllegalArgumentException();
        }
        this.coefficient = coefficient;
        this.exponent= exponent;


    }
    @Override
    public double getCoefficient() {
        return this.coefficient;
    }

    @Override
    public int getExponent() {
        return this.exponent;
    }

    @Override
    public double evaluate(double x) {
        return (this.getCoefficient()*Math.pow(x,this.getExponent()));
    }

    @Override
    public void setCoefficient(double coefficient){
        this.coefficient = coefficient;
    }
    @Override
    public void setExponent(int exponent){
        this.exponent = exponent;
    }
}

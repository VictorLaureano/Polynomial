import edu.uprm.cse.ds.list.ArrayList;

import java.util.Iterator;

public class PolynomialImp implements Polynomial{
    private ArrayList<Term> elements;
    private ArrayList<String> polysplit;
    private int size = 0;
    private int capacity = 0;
    private String polynomial;

    public PolynomialImp(String polynomial) {

        this.polynomial = polynomial;
        polytoArray(this.polynomial);
        size = elements.size();

        for (int i = 0 ; i<elements.size(); i++){
            if(elements.get(i).getExponent()==0){
                capacity++;
            }
            capacity=capacity+2;

        }


    }
    private void polytoArray(String s){
        String terms[] = s.split("\\+|\\s");
        this.elements = new ArrayList<>();
        for(int i = 0 ;i< terms.length;i++){
            if(terms[i].contains("x^")){
                if(terms[i].substring(0, terms[i].indexOf('x')).isEmpty()){
                    this.elements.add(new TermImp(1.0, Integer.parseInt(terms[i].substring(terms[i].indexOf('^')+1))));
                }else if(!(terms[i].charAt(0)=='0')){
                    this.elements.add(new TermImp(Double.parseDouble(terms[i].substring(0, terms[i].indexOf('x'))), Integer.parseInt(terms[i].substring(terms[i].indexOf('^') + 1))));
                }
            }else if(!(terms[i].indexOf('x') <0)){
                if(terms[i].substring(0, terms[i].indexOf('x')).isEmpty()){
                    this.elements.add(new TermImp(1.0, 1));
                }else if(!(terms[i].charAt(0)=='0')){
                    this.elements.add(new TermImp(Double.parseDouble(terms[i].substring(0, terms[i].indexOf('x'))), 1));
                }
            }else if(!(terms[i].charAt(0)=='0')){
                this.elements.add(new TermImp(Double.parseDouble(String.valueOf(terms[i])), 0));
            }

        }

    }
    public boolean exponentFinder(int e){
        for(int i =0 ; i<this.elements.size();i++){
            if(e == (this.elements.get(i).getExponent())){
                return true;
            }
        }return false;

    }
    @Override
    public String getString(){
        return polynomial;
    }
    public String toString(ArrayList<Term> P){
        String x = "x";
        String xponent = "x^";
        String base = "";
        for(int i=0; i<P.size();i++){
            if(i==0){
                if(P.get(i).getExponent()>1){
                    base += P.get(i).getCoefficient() + xponent + P.get(i).getExponent();
                }if(P.get(i).getExponent()==1){
                    base += P.get(i).getCoefficient() + x;
                }if(P.get(i).getExponent()==0){
                    base += P.get(i).getCoefficient();
                }
            }else{
                if(P.get(i).getExponent()>1){
                    base += "+" + P.get(i).getCoefficient() + xponent + P.get(i).getExponent();
                }if(P.get(i).getExponent()==1){
                    base += "+" + P.get(i).getCoefficient() + x;
                }else{
                    base += "+" + P.get(i).getCoefficient();
                }
            }

        } return base;
    }

    @Override
    public int getSize(){
        return this.elements.size();
    }
    @Override
    public Term getElement(int i){
        return this.elements.get(i);
    }
    @Override
    public Polynomial add(Polynomial P2) {
        ArrayList<Term> newPoly = new ArrayList<>();
        ArrayList<Term> target = new ArrayList<>();
        PolynomialImp P = (PolynomialImp) P2;
        ArrayList<Term> temp = new ArrayList<>();

        for(int i=0;i<P.getSize();i++){
            temp.add(P.elements.get(i));

        }
        for(int i=0;i<this.getSize();i++){
            newPoly.add(this.elements.get(i));
            target.add(this.elements.get(i));

        }

        for(int i =0;i<target.size();i++){
            for(int j=0;j<P.getSize();j++){
                if((target.get(i).getExponent()==P.elements.get(j).getExponent())){
                    if(((target.get(i).getCoefficient()+P.elements.get(j).getCoefficient())!=0)){
                        newPoly.replace(i, new TermImp(target.get(i).getCoefficient() + P.elements.get(j).getCoefficient(), this.elements.get(i).getExponent()));
                        temp.remove(P.elements.get(j));
                    }else{
                        newPoly.remove(i);
                        target.remove(i);
                        temp.remove(j);

                    }

                    }
                }
            }
        for(int i =0; i<temp.size();i++){
            newPoly.add(temp.get(i));
        }


        PolynomialImp result = new PolynomialImp(toString(newPoly));
        return result;
    }

    @Override
    public Polynomial subtract(Polynomial P2) {
        ArrayList<Term> newPoly = new ArrayList<>();
        PolynomialImp P = (PolynomialImp) P2;
        for (int i = 0; i < this.size;i++){
            for(int j = 0; j<P.size;j++){
                if(this.elements.get(i).getExponent()==P.elements.get(j).getExponent()){
                    newPoly.add(new TermImp(this.elements.get(i).getCoefficient() - P.elements.get(j).getCoefficient(), this.elements.get(i).getExponent()));
                }
            }
        }
        return new PolynomialImp(this.toString(newPoly));
    }
    @Override
    public Polynomial multiply(Polynomial P2) {
        return null;
    }

    @Override
    public Polynomial multiply(double c) {
        return null;
    }

    @Override
    public Polynomial derivative() {
        return null;
    }

    @Override
    public Polynomial indefiniteIntegral() {
        return null;
    }

    @Override
    public double definiteIntegral(double a, double b) {
        return 0;
    }

    @Override
    public int degree() {
        return 0;
    }

    @Override
    public double evaluate(double x) {
        return 0;
    }

    @Override
    public Iterator<Term> iterator() {
        return null;
    }


    public String converter(Polynomial P){
        String s = "x^";
        String s2 = "x";
        String base = "";
        for (int i = 0 ; i<this.elements.size(); i++){
            if(i==0){
                if(elements.get(i).getExponent()==0){
                    base =  base + Double.toString(elements.get(i).getCoefficient());
                }
                if(elements.get(i).getExponent()==1){
                    base = base + Double.toString(elements.get(i).getCoefficient()) + s2 + "+";
                }else{
                    base = base + Double.toString(elements.get(i).getCoefficient()) + s + Integer.toString(elements.get(i).getExponent());
                }
            }else {
                if (elements.get(i).getExponent() == 0) {
                    base = base + "+" + Double.toString(elements.get(i).getCoefficient());
                }
                if (elements.get(i).getExponent() == 1) {
                    base = base + "+" + Double.toString(elements.get(i).getCoefficient()) + s2 + "+";
                } else {
                    base = base + "+" + Double.toString(elements.get(i).getCoefficient()) + s + Integer.toString(elements.get(i).getExponent());
                }
            }
        }
        return base;
    }
}

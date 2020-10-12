import edu.uprm.cse.ds.list.ArrayList;

import java.util.Iterator;

public class PolynomialImp implements Polynomial{
    private ArrayList<Term> elements;
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


    }//My methods
    @Override
    public String getString(){
        return polynomial;
    }
    @Override
    public int getSize(){return this.elements.size();}
    @Override
    public Term getElement(int i){
        return this.elements.get(i);
    }private boolean isUnique(int exponent,ArrayList<Term> P) {
        for (int i = 0; i < P.size(); i++) {
            if (P.get(i).getExponent() == exponent) {
                return false;
            }

        }return true;
    }

    private ArrayList mult_div_sum(ArrayList<Term> P){
        return null;
    }



    private ArrayList sumMultiply(ArrayList<Term> P){

        ArrayList<Term> result = new ArrayList<>();
        for(int i = 0;i<P.size();i++) {
            for (int j = i + 1; j < P.size(); j++) {
                if ((P.get(i).getExponent() == P.get(j).getExponent())) {
                    if (P.get(i).getCoefficient() + P.get(j).getCoefficient() != 0) {
                        result.add(new TermImp(P.get(i).getCoefficient() + P.get(j).getCoefficient(), P.get(i).getExponent()));
                    }else{
                        if(i!=0){
                            P.remove(i);
                            P.remove(j);
                            i--;
                        }else{
                            P.remove(j);
                            P.remove(i);
                            j = i+1;
                        }
                    }
                }
            }


        }

//        for(int i = 0 ; i<P.size();i++){
//            int index =0;
//            if(isUnique(P.get(i).getExponent(),result)){
//                while(result.get(index).getExponent()>P.get(i).getExponent()){
//                    index++;
//                }
//                result.add(new TermImp(P.get(i).getCoefficient() , P.get(i).getExponent()),index);
//            }
//        }
        return result;
    }
    private ArrayList sumElements(ArrayList<Term> P, int n){ //Sums all repeated exponents in the list
        ArrayList<Term> result = new ArrayList<>();

        for(int i = 0;i<P.size();i++) {
                for (int j = i + 1; j < P.size(); j++) {
                    if ((P.get(i).getExponent() == P.get(j).getExponent())) {
                        if (P.get(i).getCoefficient() + P.get(j).getCoefficient() != 0) {
                            result.add(new TermImp(P.get(i).getCoefficient() + P.get(j).getCoefficient(), P.get(i).getExponent()));
                        }else{
                            if(i!=0){
                                P.remove(i);
                                P.remove(j);
                                i--;
                            }else{
                                P.remove(j);
                                P.remove(i);
                                j = i+1;
                            }
                        }
                    }
                }


        }

        for(int i = 0 ; i<P.size();i++){
            int index =0;
            if(isUnique(P.get(i).getExponent(),result)){
                while(result.get(index).getExponent()>P.get(i).getExponent()){
                    index++;
                }
                result.add(new TermImp(P.get(i).getCoefficient() , P.get(i).getExponent()),index);
            }
        }
        return result;
    }
    private void polytoArray(String s){ //Converts string to polynomial Array
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
    public boolean exponentFinder(int e){ //Finds if the exponent is present in the list.
        for(int i =0 ; i<this.elements.size();i++){
            if(e == (this.elements.get(i).getExponent())){
                return true;
            }
        }return false;

    }
    public String toString(ArrayList<Term> P){ //String converter.
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
    public Polynomial add(Polynomial P2) {
        ArrayList<Term> newPoly = new ArrayList<>();
        PolynomialImp P = (PolynomialImp) P2;
        for(int i = 0; i<this.elements.size();i++){
            newPoly.add(new TermImp(this.elements.get(i).getCoefficient(),this.elements.get(i).getExponent()));
        }
        for(int  j=0; j<P.elements.size();j++){
            newPoly.add(new TermImp(P.elements.get(j).getCoefficient(),P.elements.get(j).getExponent()));
        }
        newPoly = sumElements(newPoly,this.elements.size());
        PolynomialImp result = new PolynomialImp(toString(newPoly));
        return result;

    }

    @Override
    public Polynomial subtract(Polynomial P2) {
        ArrayList<Term> newPoly = new ArrayList<>();
        PolynomialImp P = (PolynomialImp) P2;

        for(int i = 0 ; i<P.elements.size();i++){
            if(P.elements.get(i).getCoefficient()>0){
                P.elements.replace(i, new TermImp(-P.elements.get(i).getCoefficient(),P.elements.get(i).getExponent()));
            }else{
                P.elements.replace(i, new TermImp(Math.abs(P.elements.get(i).getCoefficient()),P.elements.get(i).getExponent()));
            }
        }

        for(int i = 0; i<this.elements.size();i++){
            newPoly.add(new TermImp(this.elements.get(i).getCoefficient(),this.elements.get(i).getExponent()));
        }
        for(int  j=0; j<P.elements.size();j++){
            newPoly.add(new TermImp(P.elements.get(j).getCoefficient(),P.elements.get(j).getExponent()));
        }
        newPoly = sumElements(newPoly,this.elements.size());
        PolynomialImp result = new PolynomialImp(toString(newPoly));
        return result;

    }

    @Override
    public Polynomial multiply(Polynomial P2) {
        ArrayList<Term> newPoly = new ArrayList<>();
        PolynomialImp P = (PolynomialImp) P2;

        for(int i = 0; i<this.elements.size();i++){
            for(int j =0 ; j < P.elements.size();j++) {
                newPoly.add(new TermImp(this.elements.get(i).getCoefficient() * P.elements.get(j).getCoefficient(), this.elements.get(i).getExponent() + P.elements.get(j).getExponent()));
            }
        }
        newPoly = sumElements(newPoly,this.elements.size());
        PolynomialImp result = new PolynomialImp(toString(newPoly));
        return result;
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

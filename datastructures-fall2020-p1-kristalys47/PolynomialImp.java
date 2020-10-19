import edu.uprm.cse.ds.list.ArrayList;

import java.util.Iterator;

public class PolynomialImp implements Polynomial {
    private ArrayList<Term> elements;
    private int size = 0;
    private int capacity = 0;
    private String polynomial;

    public PolynomialImp(String polynomial) {

        this.polynomial = polynomial;
        polytoArray(this.polynomial);
        size = elements.size();

        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).getExponent() == 0) {
                capacity++;
            }
            capacity = capacity + 2;

        }


    }//My methods

    @Override
    public String getString() {
        return polynomial;
    }

    @Override
    public int getSize() { //Gives size of array.
        return this.elements.size();
    }

    @Override
    public Term getElement(int i) { //Gets element on position i.
        return this.elements.get(i);
    }

    private boolean isUnique(int exponent, ArrayList<Term> P) {//Checks if this exponent is found in Array.
        for (int i = 0; i < P.size(); i++) {
            if (P.get(i).getExponent() == exponent) {
                return false;
            }

        }
        return true;
    }


    private ArrayList sumElements(ArrayList<Term> P, int n) { //Sums all repeated exponents in the list
        ArrayList<Term> result = new ArrayList<>();

        boolean flag = false;

        for (int i = 0; i < P.size(); i++) { //Sums elements of same exponents, if the coefficient is not 0.
            if(flag){
                i--;
                flag=false;
            }
            for (int j = i + 1; j < P.size(); j++) {
                if ((P.get(i).getExponent() == P.get(j).getExponent())) {
                    if (P.get(i).getCoefficient() + P.get(j).getCoefficient() != 0) {
                        P.replace(i, new TermImp(P.get(i).getCoefficient() + P.get(j).getCoefficient(), P.get(i).getExponent()));
                        P.remove(j);
                        j--;
                    } else {
                        P.remove(j);
                        P.remove(i);
                        j=i+1;
                        flag = true;
                        }
                    }
                }
            }




        for (int k = 0; k < P.size(); k++) { //Adds unique exponents from polynomial that was added.
            int index = 0;
            if (isUnique(P.get(k).getExponent(), result)) {
                while (index < result.size() && result.get(index).getExponent() > P.get(k).getExponent()) {
                    index++;
                }
                result.add(new TermImp(P.get(k).getCoefficient(), P.get(k).getExponent()), index);
            }
        }
        return result;
    }

    private void polytoArray(String s) { //Converts string to polynomial Array
        String terms[] = s.split("\\+|\\s"); //Splits String by + sign and trims any extra space that the user might add.
        this.elements = new ArrayList<>();
        for (int i = 0; i < terms.length; i++) {
            if (terms[i].contains("x^")) { //All of the possible string outcomes taken into consideration before converting them to ArrayList<Terms>.
                if (terms[i].substring(0, terms[i].indexOf('x')).isEmpty()) {
                    this.elements.add(new TermImp(1.0, Integer.parseInt(terms[i].substring(terms[i].indexOf('^') + 1))));
                } else if (!terms[i].substring(0, terms[i].indexOf('x')).equals("0")) {
                    this.elements.add(new TermImp(Double.parseDouble(terms[i].substring(0, terms[i].indexOf('x'))), Integer.parseInt(terms[i].substring(terms[i].indexOf('^') + 1))));
                }
            } else if (!(terms[i].indexOf('x') < 0)) {
                if (terms[i].substring(0, terms[i].indexOf('x')).isEmpty()) {
                    this.elements.add(new TermImp(1.0, 1));
                } else if (!terms[i].substring(0, terms[i].indexOf('x')).equals("0")) {
                    this.elements.add(new TermImp(Double.parseDouble(terms[i].substring(0, terms[i].indexOf('x'))), 1));
                }
            } else if (!(terms[i].equals("0")) && !terms[i].equals("")) {
                this.elements.add(new TermImp(Double.parseDouble(String.valueOf(terms[i])), 0));
            }
        }

    }

    public boolean exponentFinder(int e) { //Finds if the exponent is present in the list.
        for (int i = 0; i < this.elements.size(); i++) {
            if (e == (this.elements.get(i).getExponent())) {
                return true;
            }
        }
        return false;

    }

    public String getString(ArrayList<Term> P) { //String converter.
        if(P.isEmpty()){
            return "0";
        }
        String x = "x";
        String xponent = "x^";
        String base = "";
        if (P.get(0).getExponent() > 1) {
            base += P.get(0).getCoefficient() + xponent + P.get(0).getExponent();
        }
        else if (P.get(0).getExponent() == 1) {
            base += P.get(0).getCoefficient() + x;
        }
        else if (P.get(0).getExponent() == 0) {
            base += P.get(0).getCoefficient();
        }
        for(int i = 1; i < P.size(); i++) {
                if (P.get(i).getExponent() > 1) {
                    base += "+" + P.get(i).getCoefficient() + xponent + P.get(i).getExponent();
                } else if (P.get(i).getExponent() == 1) {
                    base += "+" + P.get(i).getCoefficient() + x;
                } else if (P.get(i).getExponent() == 0) {
                    base += "+" + P.get(i).getCoefficient();
                }
        }
        return base;
    }

    @Override
    public Polynomial add(Polynomial P2) { //Appends target polynomial with parameter polynomial, then adds it.
        ArrayList<Term> newPoly = new ArrayList<>();
        PolynomialImp P = (PolynomialImp) P2;
        for (int i = 0; i < this.elements.size(); i++) {
            newPoly.add(new TermImp(this.elements.get(i).getCoefficient(), this.elements.get(i).getExponent()));
        }
        for (int j = 0; j < P.elements.size(); j++) {
            newPoly.add(new TermImp(P.elements.get(j).getCoefficient(), P.elements.get(j).getExponent()));
        }
        newPoly = sumElements(newPoly, this.elements.size());
        PolynomialImp result = new PolynomialImp(getString(newPoly));
        return result;

    }

    @Override
    public Polynomial subtract(Polynomial P2) { //Appends target polynomial with parameter polynomial, then adds it.
        ArrayList<Term> newPoly = new ArrayList<>();
        PolynomialImp P = (PolynomialImp) P2;

        for (int i = 0; i < this.elements.size(); i++) {
            newPoly.add(new TermImp(this.elements.get(i).getCoefficient(), this.elements.get(i).getExponent()));
        }
        for (int j = 0; j < P.elements.size(); j++) {
            newPoly.add(new TermImp(-P.elements.get(j).getCoefficient(), P.elements.get(j).getExponent()));
        }
        newPoly = sumElements(newPoly, this.elements.size());
        PolynomialImp result = new PolynomialImp(getString(newPoly));
        return result;

    }

    @Override public Polynomial multiply(Polynomial P2) { //The multiplication between polynomials are added.
        ArrayList<Term> newPoly = new ArrayList<>();
        PolynomialImp P = (PolynomialImp) P2;
        if (P.elements.size() < this.elements.size()) {
            for (int i = 0; i < this.elements.size(); i++) {
                for (int j = 0; j < P.elements.size(); j++) {
                    newPoly.add(new TermImp(this.elements.get(i).getCoefficient() * P.elements.get(j).getCoefficient(), this.elements.get(i).getExponent() + P.elements.get(j).getExponent()));
                }
            }
        } else {
            for (int i = 0; i < P.elements.size(); i++) {
                for (int j = 0; j < this.elements.size(); j++) {
                    newPoly.add(new TermImp(P.elements.get(i).getCoefficient() * this.elements.get(j).getCoefficient(), P.elements.get(i).getExponent() + this.elements.get(j).getExponent()));
                }
            }
        }
        newPoly = sumElements(newPoly, this.elements.size());
        PolynomialImp result = new PolynomialImp(getString(newPoly));
        return result;
    }

    @Override
    public Polynomial multiply(double c) { //Multiplies polynomial by constant c.
        ArrayList<Term> newPoly = new ArrayList<>();
        if (c == 0) {
            return new PolynomialImp("0");
        }
        for (int i = 0; i < this.elements.size(); i++) {
            newPoly.add(new TermImp(this.elements.get(i).getCoefficient() * c, this.elements.get(i).getExponent()));
        }
        newPoly = sumElements(newPoly, this.elements.size());
        PolynomialImp result = new PolynomialImp(getString(newPoly));
        return result;
    }

    @Override
    public Polynomial derivative() { //Calculates derivative using power rule.
        ArrayList<Term> newPoly = new ArrayList<>();
        if (this.elements.isEmpty()) {
            return new PolynomialImp("0");
        }
        for (int i = 0; i < this.elements.size(); i++) {
            if (this.elements.get(i).getExponent() > 0) {
                newPoly.add(new TermImp(this.elements.get(i).getCoefficient() * this.elements.get(i).getExponent(), this.elements.get(i).getExponent() - 1));
            }
        }
        newPoly = sumElements(newPoly, this.elements.size());
        PolynomialImp result = new PolynomialImp(getString(newPoly));
        return result;
    }

    @Override
    public Polynomial indefiniteIntegral() { //Calculates integral.
        ArrayList<Term> newPoly = new ArrayList<>();

        for (int i = 0; i < this.elements.size(); i++) {
            newPoly.add(new TermImp((this.elements.get(i).getCoefficient())/(this.elements.get(i).getExponent()+1),this.elements.get(i).getExponent()+1));
        }
        newPoly.add(new TermImp(1, 0));
        PolynomialImp result = new PolynomialImp(getString(newPoly));
        return result;
    }

    @Override
    public double definiteIntegral(double a, double b) {//Uses last method to calculate the Definite Integral.
            ArrayList<Term> newPoly = new ArrayList<>();
            for (int i = 0; i < this.elements.size(); i++) {
                newPoly.add(new TermImp((this.elements.get(i).getCoefficient())/(this.elements.get(i).getExponent()+1),this.elements.get(i).getExponent()+1));
            }
            newPoly.add(new TermImp(1, 0));
            PolynomialImp result = new PolynomialImp(getString(newPoly));
            return result.evaluate(b) - result.evaluate(a);
        }


    @Override
    public int degree() { //Gets the highest degree (Uses for loop to get it in case the use does not put the highest degree first).
        int max_deg = this.elements.get(0).getExponent();
        for (int i = 0; i < this.elements.size(); i++) {
            if (this.elements.get(i).getExponent() > max_deg) {
                max_deg = this.elements.get(i).getExponent();
            }
        }
        return max_deg;
    }

    @Override
    public double evaluate(double x) { //Gets the value of polynomial with assigned x value.
        double sum = 0;
        for (int i = 0; i < this.elements.size(); i++) {
            sum += this.elements.get(i).evaluate(x);
        }
        return sum;
    }

    @Override
    public Iterator<Term> iterator() {
        return null;
    }

    @Override
    public String toString() {//String converter.
            String x = "x";
            String xponent = "x^";
            String base = "";
            for (int i = 0; i < this.elements.size(); i++) {
                if (i == 0) {
                    if (this.elements.get(i).getExponent() > 1) {
                        base += this.elements.get(i).getCoefficient() + "0" + xponent + this.elements.get(i).getExponent();
                    }
                    if (this.elements.get(i).getExponent() == 1) {
                        base += this.elements.get(i).getCoefficient() + "0" + x;
                    }
                    if (this.elements.get(i).getExponent() == 0) {
                        base += this.elements.get(i).getCoefficient() + "0";
                    }
                } else {
                    if (this.elements.get(i).getExponent() > 1) {
                        base += "+" + this.elements.get(i).getCoefficient() + "0" + xponent + this.elements.get(i).getExponent();
                    } else if (this.elements.get(i).getExponent() == 1) {
                        base += "+" + this.elements.get(i).getCoefficient() + "0" + x;
                    } else if (this.elements.get(i).getExponent() == 0) {
                        base += "+" + this.elements.get(i).getCoefficient() + "0";
                    }
                }

            }
            return base;
        }
    }

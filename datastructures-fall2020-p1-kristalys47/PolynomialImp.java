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
    public int getSize() {
        return this.elements.size();
    }

    @Override
    public Term getElement(int i) {
        return this.elements.get(i);
    }

    private boolean isUnique(int exponent, ArrayList<Term> P) {
        for (int i = 0; i < P.size(); i++) {
            if (P.get(i).getExponent() == exponent) {
                return false;
            }

        }
        return true;
    }


    private ArrayList sumElements(ArrayList<Term> P, int n) { //Sums all repeated exponents in the list
        ArrayList<Term> result = new ArrayList<>();

        for (int i = 0; i < P.size(); i++) {
            for (int j = i + 1; j < P.size(); j++) {
                if ((P.get(i).getExponent() == P.get(j).getExponent())) {
                    if (P.get(i).getCoefficient() + P.get(j).getCoefficient() != 0) {
                        P.replace(i, new TermImp(P.get(i).getCoefficient() + P.get(j).getCoefficient(), P.get(i).getExponent()));
                        P.remove(j);
                    } else {
                        if (i != 0) {
                            P.remove(i);
                            P.remove(j);
                            i--;
                        } else {
                            P.remove(j);
                            P.remove(i);
                            j = i + 1;
                        }
                    }
                }
            }


        }

        for (int i = 0; i < P.size(); i++) {
            int index = 0;
            if (isUnique(P.get(i).getExponent(), result)) {
                while (index < result.size() && result.get(index).getExponent() > P.get(i).getExponent()) {
                    index++;
                }
                result.add(new TermImp(P.get(i).getCoefficient(), P.get(i).getExponent()), index);
            }
        }
        return result;
    }

    private void polytoArray(String s) { //Converts string to polynomial Array
        String terms[] = s.split("\\+|\\s");
        this.elements = new ArrayList<>();
        for (int i = 0; i < terms.length; i++) {

            if (terms[i].contains("x^")) {
                if (terms[i].substring(0, terms[i].indexOf('x')).isEmpty()) {
                    this.elements.add(new TermImp(1.0, Integer.parseInt(terms[i].substring(terms[i].indexOf('^') + 1))));
                } else if (!(terms[i].charAt(0) == '0')) {
                    this.elements.add(new TermImp(Double.parseDouble(terms[i].substring(0, terms[i].indexOf('x'))), Integer.parseInt(terms[i].substring(terms[i].indexOf('^') + 1))));
                }
            } else if (!(terms[i].indexOf('x') < 0)) {
                if (terms[i].substring(0, terms[i].indexOf('x')).isEmpty()) {
                    this.elements.add(new TermImp(1.0, 1));
                } else if (!(terms[i].charAt(0) == '0')) {
                    this.elements.add(new TermImp(Double.parseDouble(terms[i].substring(0, terms[i].indexOf('x'))), 1));
                }
            } else if (!(terms[i] == "0") && !terms[i].equals("")) {
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
        String x = "x";
        String xponent = "x^";
        String base = "";
        for (int i = 0; i < P.size(); i++) {
            if (i == 0) {
                if (P.get(i).getExponent() > 1) {
                    base += P.get(i).getCoefficient() + xponent + P.get(i).getExponent();
                }
                if (P.get(i).getExponent() == 1) {
                    base += P.get(i).getCoefficient() + x;
                }
                if (P.get(i).getExponent() == 0) {
                    base += P.get(i).getCoefficient();
                }
            } else {
                if (P.get(i).getExponent() > 1) {
                    base += "+" + P.get(i).getCoefficient() + xponent + P.get(i).getExponent();
                } else if (P.get(i).getExponent() == 1) {
                    base += "+" + P.get(i).getCoefficient() + x;
                } else if (P.get(i).getExponent() == 0) {
                    base += "+" + P.get(i).getCoefficient();
                }
            }

        }
        return base;
    }

    @Override
    public Polynomial add(Polynomial P2) {
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
    public Polynomial subtract(Polynomial P2) {
        ArrayList<Term> newPoly = new ArrayList<>();
        PolynomialImp P = (PolynomialImp) P2;

        for (int i = 0; i < P.elements.size(); i++) {
            if (P.elements.get(i).getCoefficient() > 0) {
                P.elements.replace(i, new TermImp(-P.elements.get(i).getCoefficient(), P.elements.get(i).getExponent()));
            } else {
                P.elements.replace(i, new TermImp(Math.abs(P.elements.get(i).getCoefficient()), P.elements.get(i).getExponent()));
            }
        }

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
    public Polynomial multiply(Polynomial P2) {
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
    public Polynomial multiply(double c) {
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
    public Polynomial derivative() {
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
    public Polynomial indefiniteIntegral() {
        ArrayList<Term> newPoly = new ArrayList<>();

        for (int i = 0; i < this.elements.size(); i++) {
            newPoly.add(new TermImp(-this.elements.get(i).getCoefficient() / (double) (this.elements.get(i).getExponent() + 1), this.elements.get(i).getExponent() + 1));
        }
        newPoly.add(new TermImp(1, 0));
        newPoly = sumElements(newPoly, this.elements.size());
        PolynomialImp result = new PolynomialImp(getString(newPoly));
        return result;
    }

    @Override
    public double definiteIntegral(double a, double b) {
        Polynomial P = this.indefiniteIntegral();
        return Math.abs(P.evaluate(b) - P.evaluate(a));
    }

    @Override
    public int degree() {
        int max_deg = this.elements.get(0).getExponent();
        for (int i = 0; i < this.elements.size(); i++) {
            if (this.elements.get(i).getExponent() > max_deg) {
                max_deg = this.elements.get(i).getExponent();
            }
        }
        return max_deg;
    }

    @Override
    public double evaluate(double x) {
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

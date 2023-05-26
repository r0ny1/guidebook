package com.mirea.informatics.ui.apps.solver;

public class Implicant implements Comparable {
    private int v;     // value = 1 indicate that non complement and 0 complement variable
    private int m;     // mask = 1 indicate don't care, 0 indicate that the term is importante
    private boolean c; // covered by a other implicante

    /**
     * Maximum number of input variables. To change this will imply in a lot of code review.
     */
    public static final int MAX_IN_VAR = 16;
    private static int number_of_in_var;
    private static final StringBuilder [] invar_e = new StringBuilder [MAX_IN_VAR];
    private static final StringBuilder [] invar_ec = new StringBuilder [MAX_IN_VAR];

    public Implicant(int v, int m, boolean covered) {
        this.v = v;
        this.m = m ;
        this.c = covered;
    }
    public Implicant(int v, int m) {
        this.v = v;
        this.m = m ;
        c = false;
    }
    public Implicant(int v) {
        this.v = v;
        m = 0;
        c = false;
    }
    
    @Override
    public boolean equals(Object o) {
        return (o instanceof Implicant) && (v == ((Implicant)o).v) && (m == ((Implicant)o).m);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.v;
        hash = 79 * hash + this.m;
        return hash;
    }
    public int bitCount_m() {
        return Integer.bitCount(m);
    }
    public int bitCount_v() {
        return Integer.bitCount(v);
    }
    
    /*
     * Initializes variable name lists (complemented and non-complemented), so
     * they are ready to be used in the subexpression of the prime implicant.
     */
    public static void startExpression(int number_of_in_vars, String [] in_var_names) {

        number_of_in_var = number_of_in_vars;
        String tmp;
        for(int i = 0; i < number_of_in_var; i++) {
            tmp = in_var_names[i].trim();
            invar_e[i] = new StringBuilder(tmp);
            String vars[] = tmp.split("_");
            StringBuilder tmpc = new StringBuilder();
           // for(int j = 0; j < vars[0].length(); j++) {
                //tmpc.append(vars[0].charAt(j));
                tmpc.append("~");
                tmpc.append(tmp);
           // }
          //  for(int j = 1; j < vars.length; j++) {
            //    tmpc.append("_");
             //   tmpc.append(vars[j]);
           // }
            invar_ec[i] = tmpc;
        }

    }

    /*
     * check if the implicant is a prime implicant, i.e., cannot be
     * represented by a simpler (shorter) implicant
     */
    public boolean isPrime() {
        return !c;
    }
    /*
     * check if the implicant is true for attribution x
     */
    public boolean isTrue(int x){
        return ((v ^ x) & (~m) & 0x7F_FF_FF_FF) == 0;  // up to 31 variables
    }

    public int getV() {
        return v;
    }
    public int getM() {
        return m;
    }
    public void setC(boolean c) {
        this.c = c;
    }

    /*
     * Express the implicant as a boolean expression (product)
     */
    public StringBuilder toExpressionProd(){
        StringBuilder buf = new StringBuilder();
        for(int i = number_of_in_var - 1; i >= 0 ; i--){ // start at the most significative bit
            if((m & (1 << i)) == 0) {
                if((v & (1 << i)) == 0) {
                    if (buf.length() != 0)
                        buf.append(" * ");
                    buf.append(invar_ec[number_of_in_var - i - 1]);
                }
                else {
                    if (buf.length() != 0)
                        buf.append(" * ");
                    buf.append(invar_e[number_of_in_var - i - 1]);
                }
            }
        }
        return buf;
    }
    
    /*
     * Express the implicant as a boolean expression (sum)
     */
    public StringBuilder toExpressionSum(){
        StringBuilder buf = new StringBuilder();
        for(int i = number_of_in_var - 1; i >= 0 ; i--){ // start at the most significative bit
            if((m & (1 << i)) == 0) {
                if((v & (1 << i)) == 0) {
                    if (buf.length() != 0)
                        buf.append(" + ");
                    buf.append(invar_e[number_of_in_var - i - 1]);
                }
                else {
                    if (buf.length() != 0)
                        buf.append(" + ");
                    buf.append(invar_ec[number_of_in_var - i - 1]);
                }
            }
        }
        return buf;
    }

    @Override
    public String toString() {
        String ret = "m(" + Integer.toString(v);
        if (m > 0) {
            int nb = Integer.bitCount(m);
            int mx = 1 << nb;
            int[] ms = new int[MAX_IN_VAR];
            int msp = 0;

            for(int j = 0; msp < nb; j++)
                if ((m & 1 << j) != 0) {
                    ms[msp] = 1 << j;
                    msp++;
                }
                        
            for(int i = 1;i < mx; i++) {
                int ed = 0;
                for(int j = 0; j < nb; j++)
                    if (((1 << j) & i) != 0)
                        ed |= ms[j];
                ed |= v;
                ret += "," + Integer.toString(ed);
            }
        }
        ret += ")" + (c ? " " : "*");
        return ret;
    }

    @Override
    public int compareTo(Object o) {
        if(Integer.bitCount(((Implicant)o).m) != Integer.bitCount(m))
            return Integer.bitCount(((Implicant)o).m) - Integer.bitCount(m);
        if (m != ((Implicant)o).m)  // first variable is presente, then if it is complemented or not
            return m - ((Implicant)o).m;
        return ((Implicant)o).v - v;
    }
}

package com.mirea.informatics.ui.apps.solver;

import java.util.ArrayList;

public class Solutions {

    ArrayList<Implicant> essentialsPI;
    ArrayList<ArrayList<Implicant>> primeI;
    
    Solutions(){
        essentialsPI = null;
        primeI = null;
    }

    public void setEssentialPI(ArrayList<Implicant> essentialsPI) {
        this.essentialsPI = essentialsPI;
    }

    public void setPIsize(int s) {
        primeI = new ArrayList <>(s);
    }

    public boolean addSolution(int size) {
        return primeI.add(new ArrayList<Implicant>(size));
    }

    boolean addPI(int lst, Implicant p){
        return primeI.get(lst).add(p);
    }

    public ArrayList<Implicant> getEssentialsPI(){
        return essentialsPI;
    }

    public ArrayList <ArrayList<Implicant>> getPrimeI(){
        return primeI;
    }
}
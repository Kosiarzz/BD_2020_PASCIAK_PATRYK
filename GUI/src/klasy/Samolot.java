/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klasy;

/**
 *
 * @author USER
 */
public class Samolot {
    
    int ID,miejsc;
    String model, rodzaj;

    public Samolot(int ID, int miejsc, String model, String rodzaj) {
        this.ID = ID;
        this.miejsc = miejsc;
        this.model = model;
        this.rodzaj = rodzaj;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getMiejsc() {
        return miejsc;
    }

    public void setMiejsc(int miejsc) {
        this.miejsc = miejsc;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getRodzaj() {
        return rodzaj;
    }

    public void setRodzaj(String rodzaj) {
        this.rodzaj = rodzaj;
    }
    
    
    
    
}

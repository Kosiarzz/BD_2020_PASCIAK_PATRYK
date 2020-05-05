package klasy;


public class Zarezerwowane {
    
    
    int nr_rezerwacji, nr_lotu, nr_miejsca, ID;
    String start,z,ladowanie,w,samolot,status;  

    public Zarezerwowane(int ID, int nr_rezerwacji, int nr_lotu, int nr_miejsca, String start, String z, String ladowanie, String w, String samolot, String status) {
        this.ID = ID;
        this.nr_rezerwacji = nr_rezerwacji;
        this.nr_lotu = nr_lotu;
        this.nr_miejsca = nr_miejsca;
        this.start = start;
        this.z = z;
        this.ladowanie = ladowanie;
        this.w = w;
        this.samolot = samolot;
        this.status = status;
    }

    public int getID(){
        return ID;
    }
    
    public void setID(int ID){
        this.ID = ID;
    }
    
    public int getNr_rezerwacji() {
        return nr_rezerwacji;
    }

    public void setNr_rezerwacji(int nr_rezerwacji) {
        this.nr_rezerwacji = nr_rezerwacji;
    }

    public int getNr_lotu() {
        return nr_lotu;
    }

    public void setNr_lotu(int nr_lotu) {
        this.nr_lotu = nr_lotu;
    }

    public int getNr_miejsca() {
        return nr_miejsca;
    }

    public void setNr_miejsca(int nr_miejsca) {
        this.nr_miejsca = nr_miejsca;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getZ() {
        return z;
    }

    public void setZ(String z) {
        this.z = z;
    }

    public String getLadowanie() {
        return ladowanie;
    }

    public void setLadowanie(String ladowanie) {
        this.ladowanie = ladowanie;
    }

    public String getW() {
        return w;
    }

    public void setW(String w) {
        this.w = w;
    }

    public String getSamolot() {
        return samolot;
    }

    public void setSamolot(String samolot) {
        this.samolot = samolot;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
    
}

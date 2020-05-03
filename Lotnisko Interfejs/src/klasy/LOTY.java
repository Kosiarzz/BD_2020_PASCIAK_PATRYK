package klasy;

public class LOTY {
    
    
    int ID_LOT,NUMER_LOTU,DOSTEPNE_MIEJSCA;
    String model,startowanie,ladowanie,powrot,status,z,DO;

    public LOTY(int ID_LOT, int NUMER_LOTU, int DOSTEPNE_MIEJSCA, String model, String startowanie, String ladowanie, String powrot, String status, String z, String DO) {
        this.ID_LOT = ID_LOT;
        this.NUMER_LOTU = NUMER_LOTU;
        this.DOSTEPNE_MIEJSCA = DOSTEPNE_MIEJSCA;
        this.model = model;
        this.startowanie = startowanie;
        this.ladowanie = ladowanie;
        this.powrot = powrot;
        this.status = status;
        this.z = z;
        this.DO = DO;
    }

    public int getID_LOT() {
        return ID_LOT;
    }

    public void setID_LOT(int ID_LOT) {
        this.ID_LOT = ID_LOT;
    }

    public int getNUMER_LOTU() {
        return NUMER_LOTU;
    }

    public void setNUMER_LOTU(int NUMER_LOTU) {
        this.NUMER_LOTU = NUMER_LOTU;
    }

    public int getDOSTEPNE_MIEJSCA() {
        return DOSTEPNE_MIEJSCA;
    }

    public void setDOSTEPNE_MIEJSCA(int DOSTEPNE_MIEJSCA) {
        this.DOSTEPNE_MIEJSCA = DOSTEPNE_MIEJSCA;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getStartowanie() {
        return startowanie;
    }

    public void setStartowanie(String startowanie) {
        this.startowanie = startowanie;
    }

    public String getLadowanie() {
        return ladowanie;
    }

    public void setLadowanie(String ladowanie) {
        this.ladowanie = ladowanie;
    }

    public String getPowrot() {
        return powrot;
    }

    public void setPowrot(String powrot) {
        this.powrot = powrot;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getZ() {
        return z;
    }

    public void setZ(String z) {
        this.z = z;
    }

    public String getDO() {
        return DO;
    }

    public void setDO(String DO) {
        this.DO = DO;
    }
    
    
    
    
}

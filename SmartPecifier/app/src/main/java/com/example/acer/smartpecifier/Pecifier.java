package com.example.acer.smartpecifier;

public class Pecifier {

    public Pecifier() {
        super();
    }


    private int ID;
    private String READ_KEY;
    private String WRITE_KEY;
    private String BABY_NAME;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getREAD_KEY() {
        return READ_KEY;
    }

    public void setREAD_KEY(String READ_KEY) {
        this.READ_KEY = READ_KEY;
    }

    public String getWRITE_KEY() {
        return WRITE_KEY;
    }

    public void setWRITE_KEY(String WRITE_KEY) {
        this.WRITE_KEY = WRITE_KEY;
    }

    public String getBABY_NAME() {
        return BABY_NAME;
    }

    public void setBABY_NAME(String BABY_NAME) {
        this.BABY_NAME = BABY_NAME;
    }

    public Pecifier(int ID, String READ_KEY, String WRITE_KEY, String BABY_NAME) {
        this.ID = ID;
        this.READ_KEY = READ_KEY;
        this.WRITE_KEY = WRITE_KEY;
        this.BABY_NAME = BABY_NAME;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Pecifier other = (Pecifier) obj;
        if (ID != other.ID)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Pecifier [id=" + ID + ", name=" + BABY_NAME + ", READ_KEY="
                + READ_KEY + ", WRITE_KEY=" + WRITE_KEY + "]";
    }



}

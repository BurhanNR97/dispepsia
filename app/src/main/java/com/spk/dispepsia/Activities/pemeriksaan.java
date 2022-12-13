package com.spk.dispepsia.Activities;

public class pemeriksaan {
    String id = null;
    String kode = null;
    String gejala = null;
    String bobot = null;
    boolean selected = false;

    public pemeriksaan() {};

    public pemeriksaan(String id, String kode, String gejala, String bobot, boolean selected) {
        super();
        this.id = id;
        this.kode = kode;
        this.gejala = gejala;
        this.bobot = bobot;
        this.selected = selected;
    }

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getGejala() {
        return gejala;
    }

    public void setGejala(String gejala) {
        this.gejala = gejala;
    }

    public String getBobot() {
        return bobot;
    }

    public void setBobot(String bobot) {
        this.bobot = bobot;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}

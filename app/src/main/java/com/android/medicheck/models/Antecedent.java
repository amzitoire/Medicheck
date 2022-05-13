package com.android.medicheck.models;

public class Antecedent {
    private int id_antecedent;
    private String description;
    private int id_dossier;
    public Dossier dossier;

    public String getDescription() {
        return description;
    }

    public int getId_dossier() {
        return id_dossier;
    }

    public void setId_dossier(int id_dossier) {
        this.id_dossier = id_dossier;
    }

    public Dossier getDossier() {
        return dossier;
    }

    public void setDossier(Dossier dossier) {
        this.dossier = dossier;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId_antecedent() {
        return id_antecedent;
    }

    public void setId_antecedent(int id_antecedent) {
        this.id_antecedent = id_antecedent;
    }


}

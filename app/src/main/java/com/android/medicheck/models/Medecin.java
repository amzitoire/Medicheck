package com.android.medicheck.models;

import java.util.ArrayList;
import java.util.List;

public class Medecin {
    public int id_medecin;

    public String nom;

    public String prenom;

    public String specialite;

    private int numero;

    public List<Hopital> hopital = new ArrayList<Hopital> ();

    public int getId_medecin() {
        return id_medecin;
    }

    public void setId_medecin(int id_medecin) {
        this.id_medecin = id_medecin;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public List<Hopital> getHopital() {
        return hopital;
    }

    public void setHopital(List<Hopital> hopital) {
        this.hopital = hopital;
    }
}


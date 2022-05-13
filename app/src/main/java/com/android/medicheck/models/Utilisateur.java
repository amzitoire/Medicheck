package com.android.medicheck.models;


public abstract class Utilisateur {
        private int id;

        private String nom;

        private String prenom;
        private String login;

        private String password;

        private String type;

        public void connect(String login, String password) {
        }

}

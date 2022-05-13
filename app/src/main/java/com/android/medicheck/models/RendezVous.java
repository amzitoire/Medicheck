package com.android.medicheck.models;

import java.util.Date;

public class RendezVous {

private String id_rv;

private Date date_rv;

private String motif_rv;

private String id_consulation;

private String statuts;

public Consultation consultation;

        public String getId_rv() {
                return id_rv;
        }

        public void setId_rv(String id_rv) {
                this.id_rv = id_rv;
        }

        public Date getDate_rv() {
                return date_rv;
        }

        public void setDate_rv(Date date_rv) {
                this.date_rv = date_rv;
        }

        public String getMotif_rv() {
                return motif_rv;
        }

        public void setMotif_rv(String motif_rv) {
                this.motif_rv = motif_rv;
        }

        public String getId_consulation() {
                return id_consulation;
        }

        public void setId_consulation(String id_consulation) {
                this.id_consulation = id_consulation;
        }

        public String getStatuts() {
                return statuts;
        }

        public void setStatuts(String statuts) {
                this.statuts = statuts;
        }

        public Consultation getConsultation() {
                return consultation;
        }

        public void setConsultation(Consultation consultation) {
                this.consultation = consultation;
        }
}
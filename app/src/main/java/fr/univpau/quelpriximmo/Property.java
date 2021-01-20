package fr.univpau.quelpriximmo;

import java.util.Date;

public class Property {

    // Vente
    Date dateMutation;
    String natureMutation;
    String valeurFonciere;

    // Adresse
    String numVoie;
    String sufNum;
    String typeVoie;
    String voie;
    String codePostal;
    String commune;

    // Données
    String surfLot1;
    String surfLot2;
    String surfLot3;
    String surfLot4;
    String surfLot5;
    String typeLocal;
    String surfReelleBatie;
    String nbPieces;
    String surfTerrain;

    // Divers
    String parcelleCada;

    //Constructor


    public Property(Date dateMutation, String natureMutation, String valeurFonciere, String numVoie, String sufNum, String typeVoie, String voie, String codePostal, String commune, String surfLot1, String surfLot2, String surfLot3, String surfLot4, String surfLot5, String typeLocal, String surfReelleBatie, String nbPieces, String surfTerrain, String parcelleCada) {
        this.dateMutation = dateMutation;
        this.natureMutation = natureMutation;
        this.valeurFonciere = valeurFonciere;
        this.numVoie = numVoie;
        this.sufNum = sufNum;
        this.typeVoie = typeVoie;
        this.voie = voie;
        this.codePostal = codePostal;
        this.commune = commune;
        this.surfLot1 = surfLot1;
        this.surfLot2 = surfLot2;
        this.surfLot3 = surfLot3;
        this.surfLot4 = surfLot4;
        this.surfLot5 = surfLot5;
        this.typeLocal = typeLocal;
        this.surfReelleBatie = surfReelleBatie;
        this.nbPieces = nbPieces;
        this.surfTerrain = surfTerrain;
        this.parcelleCada = parcelleCada;
    }

    // Getters and Setters


    public Date getDateMutation() {
        return dateMutation;
    }

    public void setDateMutation(Date dateMutation) {
        this.dateMutation = dateMutation;
    }

    public String getNatureMutation() {
        return natureMutation;
    }

    public void setNatureMutation(String natureMutation) {
        this.natureMutation = natureMutation;
    }

    public String getValeurFonciere() {
        return valeurFonciere;
    }

    public void setValeurFonciere(String valeurFonciere) {
        this.valeurFonciere = valeurFonciere;
    }

    public String getNumVoie() {
        return numVoie;
    }

    public void setNumVoie(String numVoie) {
        this.numVoie = numVoie;
    }

    public String getSufNum() {
        return sufNum;
    }

    public void setSufNum(String sufNum) {
        this.sufNum = sufNum;
    }

    public String getTypeVoie() {
        return typeVoie;
    }

    public void setTypeVoie(String typeVoie) {
        this.typeVoie = typeVoie;
    }

    public String getVoie() {
        return voie;
    }

    public void setVoie(String voie) {
        this.voie = voie;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getCommune() {
        return commune;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    public String getSurfLot1() {
        return surfLot1;
    }

    public void setSurfLot1(String surfLot1) {
        this.surfLot1 = surfLot1;
    }

    public String getSurfLot2() {
        return surfLot2;
    }

    public void setSurfLot2(String surfLot2) {
        this.surfLot2 = surfLot2;
    }

    public String getSurfLot3() {
        return surfLot3;
    }

    public void setSurfLot3(String surfLot3) {
        this.surfLot3 = surfLot3;
    }

    public String getSurfLot4() {
        return surfLot4;
    }

    public void setSurfLot4(String surfLot4) {
        this.surfLot4 = surfLot4;
    }

    public String getSurfLot5() {
        return surfLot5;
    }

    public void setSurfLot5(String surfLot5) {
        this.surfLot5 = surfLot5;
    }

    public String getTypeLocal() {
        return typeLocal;
    }

    public void setTypeLocal(String typeLocal) {
        this.typeLocal = typeLocal;
    }

    public String getSurfReelleBatie() {
        return surfReelleBatie;
    }

    public void setSurfReelleBatie(String surfReelleBatie) {
        this.surfReelleBatie = surfReelleBatie;
    }

    public String getNbPieces() {
        return nbPieces;
    }

    public void setNbPieces(String nbPieces) {
        this.nbPieces = nbPieces;
    }

    public String getSurfTerrain() {
        return surfTerrain;
    }

    public void setSurfTerrain(String surfTerrain) {
        this.surfTerrain = surfTerrain;
    }

    public String getParcelleCada() {
        return parcelleCada;
    }

    public void setParcelleCada(String parcelleCada) {
        this.parcelleCada = parcelleCada;
    }
}

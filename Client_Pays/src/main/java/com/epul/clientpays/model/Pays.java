package com.epul.clientpays.model;

public class Pays {
	private String nom;
	private String nomCapital;
	private int nbHabitants;
	
	public Pays() {
		this.nom = "Inconnue";
		this.nomCapital = "Inconnue";
		this.nbHabitants = 0;
	}
	
	public Pays(String nom) {
		this.nom = nom;
		this.nomCapital = "Inconnue";
		this.nbHabitants = 0;
	}

	public Pays(String nom, String nomCapital, int nbHabitants) {
		this.nom = nom;
		this.nomCapital = nomCapital;
		this.nbHabitants = nbHabitants;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public String getNomCapital() {
		return nomCapital;
	}

	public void setNomCapital(String nomCapital) {
		this.nomCapital = nomCapital;
	}

	public int getNbHabitants() {
		return nbHabitants;
	}

	public void setNbHabitants(int nbHabitants) {
		this.nbHabitants = nbHabitants;
	}

}

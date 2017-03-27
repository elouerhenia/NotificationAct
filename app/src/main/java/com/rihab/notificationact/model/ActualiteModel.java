package com.rihab.notificationact.model;

/**
 * Created by faten on 23/03/2017.
 */

public class ActualiteModel {
	private int id_actu;
	private String titre_actu;
	private String description_actu;
	private String date_actu;
	private String heure_actu;
	private String couleur_actu;
	private String auteurNom_actu;
	private String auteurPrenom_actu;
	private String type_actu;

	public String getAuteurNom_actu() {
		return auteurNom_actu;
	}

	public void setAuteurNom_actu(String auteurNom_actu) {
		this.auteurNom_actu = auteurNom_actu;
	}

	public String getAuteurPrenom_actu() {
		return auteurPrenom_actu;
	}

	public void setAuteurPrenom_actu(String auteurPrenom_actu) {
		this.auteurPrenom_actu = auteurPrenom_actu;
	}

	public int getId_actu() {
		return id_actu;
	}

	public void setId_actu(int id_actu) {
		this.id_actu = id_actu;
	}

	public String getTitre_actu() {
		return titre_actu;
	}

	public void setTitre_actu(String titre_actu) {
		this.titre_actu = titre_actu;
	}

	public String getDescription_actu() {
		return description_actu;
	}

	public void setDescription_actu(String description_actu) {
		this.description_actu = description_actu;
	}

	public String getDate_actu() {
		return date_actu;
	}

	public void setDate_actu(String date_actu) {
		this.date_actu = date_actu;
	}

	public String getHeure_actu() {
		return heure_actu;
	}

	public void setHeure_actu(String heure_actu) {
		this.heure_actu = heure_actu;
	}

	public String getCouleur_actu() {
		return couleur_actu;
	}

	public void setCouleur_actu(String couleur_actu) {
		this.couleur_actu = couleur_actu;
	}


	public String getType_actu() {
		return type_actu;
	}

	public void setType_actu(String type_actu) {
		this.type_actu = type_actu;
	}


}

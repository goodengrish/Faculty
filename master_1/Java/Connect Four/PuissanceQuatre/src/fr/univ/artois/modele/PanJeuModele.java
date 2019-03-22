package fr.univ.artois.modele;

import java.util.ArrayList;

import fr.univ.artois.vue.Observer;

/**
 * Panel de jeu général possèdant une grille
 * @author adam
 *
 */
public class PanJeuModele extends AbstractModele implements Observable{
	
	public int[][] getGrille() {
		return grille;
	}

	public void setGrille(int[][] grille) {
		this.grille = grille;
	}

	public int getTourJoueur() {
		return tourJoueur;
	}

	public void setTourJoueur(int tourJoueur) {
		this.tourJoueur = tourJoueur;
	}

	public AnalyseurLignes getAnalyseur() {
		return analyseur;
	}

	public void setAnalyseur(AnalyseurLignes analyseur) {
		this.analyseur = analyseur;
	}

	//1 pour le joueur jaune et 2 pour rouge
	protected int[][] grille = new int[6][7];
	protected int tourJoueur = 1;
	protected AnalyseurLignes analyseur = new AnalyseurLignes();
	
	public PanJeuModele(){
		
	}
	
	public void addObserver(Observer obs){
		this.observers.add(obs);
	}
	
	public void notifyObserver(String str, int i, int j,int tour){
		for(Observer obs : observers){
			obs.update(str, i, j, tour);
		}
	}
	
	public void removeObserver(){
		this.observers = new ArrayList<Observer>();
	}

}

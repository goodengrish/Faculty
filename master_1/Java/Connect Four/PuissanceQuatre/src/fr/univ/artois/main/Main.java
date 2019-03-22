package fr.univ.artois.main;
import fr.univ.artois.vue.*;
import fr.univ.artois.controler.*;
import fr.univ.artois.modele.*;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PanJeuModele panJeu = new PanJeuModele();
		FenetreJeuControler controler = new FenetreJeuControler(panJeu);
		FenetreDeJeu jeu = new FenetreDeJeu(controler);
		panJeu.addObserver(jeu);
	}

}

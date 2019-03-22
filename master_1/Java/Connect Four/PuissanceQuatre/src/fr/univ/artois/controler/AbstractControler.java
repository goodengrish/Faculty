package fr.univ.artois.controler;

import fr.univ.artois.modele.AbstractModele;
import fr.univ.artois.modele.PanJeuModele;

public abstract class AbstractControler {
	
	protected PanJeuModele fenetreJeu;
	
	public PanJeuModele getFenetreJeu() {
		return fenetreJeu;
	}

	public void setFenetreJeu(PanJeuModele fenetreJeu) {
		this.fenetreJeu = fenetreJeu;
	}
	
}

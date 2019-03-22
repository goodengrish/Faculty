package fr.univ.artois.modele;

import fr.univ.artois.vue.*;

/**
 * Interface implémentée par le modèle
 * La méthode notifyObserver permet d'envoyer les infos nécessaires à la vue qui sera modifiée grâce à cela
 * @author adam
 *
 */
public interface Observable {
	
	public void addObserver(Observer obs);
	public void removeObserver();
	public void notifyObserver(String str, int i, int j, int tour);

}

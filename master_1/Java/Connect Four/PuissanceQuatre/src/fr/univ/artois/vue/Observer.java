package fr.univ.artois.vue;

/**
 * Interface implémentée par la vue et qui "surveille" le modèle pour pouvoir modifier la vue en conséquence 
 * @author adam
 *
 */
public interface Observer {

	public void update(String str, int i, int j, int tour);

}

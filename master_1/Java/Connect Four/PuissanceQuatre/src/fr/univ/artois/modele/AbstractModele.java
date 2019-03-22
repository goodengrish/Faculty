package fr.univ.artois.modele;

import java.util.ArrayList;

import fr.univ.artois.vue.Observer;

/**
 * Modele abstrait qui sert de squelette à la grille
 * @author adam
 *
 */
public abstract class AbstractModele{
	
	protected ArrayList<Observer> observers = new ArrayList<Observer>();
	
}

package fr.univ.artois.vue;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * Vue
 * Grille contenant les cellules 
 * @author adam
 *
 */
public class Grille extends JPanel{
	
	protected boolean estTermine = false;
	protected int taille = 42;
	
	public Grille(){
		
		this.setLayout(new GridLayout(6,7));
		
		Cellule tmp = null;
		for(int i = 0; i < this.taille; ++i){
			tmp = new Cellule();
			this.add(tmp);
		}
	}

	public boolean isEstTermine() {
		return estTermine;
	}

	public void setEstTermine(boolean estTermine) {
		this.estTermine = estTermine;
	}

	public int getTaille() {
		return taille;
	}

	public void setTaille(int taille) {
		this.taille = taille;
	}
	
}

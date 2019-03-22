package fr.univ.artois.vue;

import javax.swing.JPanel;

import java.awt.CardLayout;

/**
 * Panel qui contiendra tous les autres panel (menu, aide, jeu)
 * @author adam
 *
 */
public class PanelPrincipal extends JPanel{
	
	protected Grille grille = new Grille();
	
	public PanelPrincipal(){
		this.setLayout(new CardLayout());
		this.add(this.grille);
	}
	
}

package fr.univ.artois.vue;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelFinDePartie extends JPanel{
	
	protected JLabel fin = new JLabel(new ImageIcon("Sprites/gameover.png"));
	protected JLabel gagne = new JLabel("Match nul");
	
	/**
	 * Constructeur par défaut d'un match nul
	 */
	public PanelFinDePartie(){
		
		this.setLayout(new GridLayout(2,1));
		this.fin.setHorizontalAlignment(JLabel.CENTER);
		this.gagne.setHorizontalAlignment(JLabel.CENTER);
		this.add(this.fin);
		this.add(this.gagne);
		this.gagne.setForeground(Color.white);
		this.setBackground(Color.BLACK);
		
	}
	
	/**
	 * Constructeur surchargé où il y a un gagnant
	 * @param g
	 */
	public PanelFinDePartie(int g){
		
	}

	public JLabel getFin() {
		return fin;
	}

	public void setFin(JLabel fin) {
		this.fin = fin;
	}

	public JLabel getGagne() {
		return gagne;
	}

	public void setGagne(JLabel gagne) {
		this.gagne = gagne;
	}
	
}

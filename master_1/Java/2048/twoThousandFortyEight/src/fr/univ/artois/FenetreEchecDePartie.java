package fr.univ.artois;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.GridLayout;

public class FenetreEchecDePartie extends JPanel{

	private JLabel msg = new JLabel();
	private JLabel score = new JLabel();
	private JLabel choix = new JLabel();
	
	public FenetreEchecDePartie(int scor){
		this.setLayout(new GridLayout(3,1));
		this.setBackground(Color.BLACK);
		this.setForeground(Color.white);
		this.msg.setText("DEFAITE !");
		this.score.setText("Vous avez obtenu le score de "+scor);
		this.choix.setText("Q pour quitter/R pour recommencer");
		this.add(this.msg);
		this.add(this.score);
		this.add(this.choix);
	}
	
}

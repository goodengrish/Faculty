package fr.univ.artois;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JLabel;

public class FenetreFinDePartie extends JPanel{
	
	private JLabel msg = new JLabel();
	private JLabel score = new JLabel();
	private JLabel choix = new JLabel();
	
	public FenetreFinDePartie(int scor){
		this.setLayout(new GridLayout(3,1));
		this.setBackground(Color.BLACK);
		this.setForeground(Color.white);
		this.msg.setText("VICTOIRE !");
		this.score.setText("Vous avez obtenu le score de "+scor);
		this.choix.setText("R pour recommencer/C pour continuer");
		this.add(this.msg);
		this.add(this.score);
		this.add(this.choix);
	}
}

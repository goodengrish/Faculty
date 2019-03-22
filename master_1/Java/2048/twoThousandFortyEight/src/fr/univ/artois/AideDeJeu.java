package fr.univ.artois;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridLayout;

public class AideDeJeu extends JPanel{
	
	private JLabel lab1 = new JLabel("Aide du 2048");
	private JLabel textF = new JLabel("COMMENT JOUER: Utilisez les flêches pour bouger les tuiles.");
	private JLabel textF2 = new JLabel("Lorsque deux tuiles de même valeur se touchent, elles fusionnent en une seule !");
	private JLabel lab2 = new JLabel("Appuyez sur H pour revenir au jeu");
	
	public AideDeJeu(){
		super();
		this.setLayout(new GridLayout(4, 1));
		this.add(this.lab1);
		this.add(this.textF);
		this.add(this.textF2);
		this.add(this.lab2);
	}
}

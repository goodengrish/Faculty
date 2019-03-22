package fr.univ.artois;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.GridLayout;

public class Introduction extends JPanel{
	
	private JButton joueur = new JButton("Joueur");
	private JButton iadebile = new JButton("IA débile");
	private JButton iaok = new JButton("IA OK");
	
	public Introduction(){
		this.setLayout(new GridLayout(1, 3));
		this.add(this.joueur);
		this.add(this.iadebile);
		this.add(this.iaok);
	}

	public JButton getIaok() {
		return iaok;
	}

	public void setIaok(JButton iaok) {
		this.iaok = iaok;
	}

	public JButton getJoueur() {
		return joueur;
	}

	public void setJoueur(JButton joueur) {
		this.joueur = joueur;
	}

	public JButton getIadebile() {
		return iadebile;
	}

	public void setIadebile(JButton iadebile) {
		this.iadebile = iadebile;
	}
	
}

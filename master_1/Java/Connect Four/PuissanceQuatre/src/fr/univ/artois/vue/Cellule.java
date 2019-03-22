package fr.univ.artois.vue;

import javax.swing.JButton;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

/**
 * Cet objet est une cellule qui a un total de trois états. Un état vide, jaune et rouge.
 * Les joueurs pourront cliquer dessus pour permettre de glisser leur jeton
 * @author adam
 *
 */
public class Cellule extends JButton{
	
	protected ImageIcon image = new ImageIcon("Sprites/vide.png");
	protected boolean estValide  = false;
	protected int id;
	public static int idUnique = 0;
	
	public Cellule(){
		this.setIcon(this.image);
		this.id = idUnique;
		idUnique++;
	}

	public static int getIdUnique() {
		return idUnique;
	}

	public static void setIdUnique(int idUnique) {
		Cellule.idUnique = idUnique;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ImageIcon getImage() {
		return image;
	}

	public void setImage(ImageIcon image) {
		this.image = image;
		this.setIcon(this.image);
		this.estValide = true;
	}

	public boolean isEstValide() {
		return estValide;
	}

	public void setEstValide(boolean estValide) {
		this.estValide = estValide;
	}

}

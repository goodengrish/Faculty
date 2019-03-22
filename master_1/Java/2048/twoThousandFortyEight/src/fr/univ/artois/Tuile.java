package fr.univ.artois;
import javax.swing.JButton;
import java.awt.Color;

public class Tuile extends JButton{
	
	private int value = -1;
	private Color color = new Color(0xcdc1b4);
	
	public Tuile(){
		this.setForeground(Color.black);
		this.setBackground(this.color);
		if (this.value != -1){
			this.setText(Integer.toString(this.value));
		}
	}

	public int getValue() {
		return value;
	}

	/**
	 * Ce setter change également la couleur de fond de la tuile lors d'un changement de valeur
	 * @param value
	 */
	public void setValue(int value) {
		this.value = value;
		
		if (this.value == -1){
			this.color = new Color(0xcdc1b4);
			this.setBackground(this.color);
			this.setText("");
		}
		
		else if (this.value == 2){
			this.color = new Color(0xeee4da);
			this.setBackground(this.color);
		}
		else if (this.value == 4){
			this.color = new Color(0xede0c8);
			this.setBackground(this.color);
		}
		else if (this.value == 8){
			this.color = new Color(0xf2b179);
			this.setBackground(this.color);
		}
		else if (this.value == 16){
			this.color = new Color(0xf59563);
			this.setBackground(this.color);
		}
		else if (this.value == 32){
			this.color = new Color(0xf67c5f);
			this.setBackground(this.color);
		}
		else if (this.value == 64){
			this.color = new Color(0xf65e3b);
			this.setBackground(this.color);
		}
		else if (this.value == 128){
			this.color = new Color(0xedcf72);
			this.setBackground(this.color);
		}
		else if (this.value == 256){
			this.color = new Color(0xedcc61);
			this.setBackground(this.color);
		}
		else if (this.value == 512){
			this.color = new Color(0xedc850);
			this.setBackground(this.color);
		}
		else if (this.value == 1024){
			this.color = new Color(0xedc53f);
			this.setBackground(this.color);
		}
		else if (this.value == 2048){
			this.color = new Color(0xedc22e);
			this.setBackground(this.color);
		}
		
		if (this.value != -1){
			this.setText(Integer.toString(this.value));
		}
		
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
}

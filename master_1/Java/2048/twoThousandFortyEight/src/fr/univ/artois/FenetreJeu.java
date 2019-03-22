package fr.univ.artois;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridLayout;

public class FenetreJeu extends JPanel{
	
	private GrilleTuiles gt = new GrilleTuiles();
	
	public FenetreJeu(){
		super();
		this.setLayout(new GridLayout(1,1));
		this.add(this.gt);
	}

	public GrilleTuiles getGt() {
		return gt;
	}

	public void setGt(GrilleTuiles gt) {
		this.gt = gt;
	}
	
}

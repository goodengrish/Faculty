package fr.univ.artois;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.util.Random;
import java.util.ArrayList;

public class GrilleTuiles extends JPanel{
	
	private Random r = new Random();
	private ArrayList<Tuile> tuiles = new ArrayList<Tuile>();
	private int tuileRand1 = -1;
	private int tuileRand2 = -1;
	private Tuile tmp = null;
	private int score = 0;
	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public GrilleTuiles(){
		super();
		
		this.tuileRand1 = r.nextInt(16);
		this.tuileRand2 = r.nextInt(16);
		
		while (this.tuileRand2 == this.tuileRand1){
			this.tuileRand2 = r.nextInt(16);
		}
		
		this.setLayout(new GridLayout(4, 4));
		
		for(int i = 0; i < 16; ++i){
			this.tmp = new Tuile();
			this.tuiles.add(this.tmp);
		}
		
		this.tuiles.get(this.tuileRand1).setValue(2);
		this.tuiles.get(this.tuileRand2).setValue(2);
		
		for(int i = 0; i < 16; ++i){
			tmp = this.tuiles.get(i);
			this.add(tmp);
		}
		
	}

	public Random getR() {
		return r;
	}

	public void setR(Random r) {
		this.r = r;
	}

	public ArrayList<Tuile> getTuiles() {
		return tuiles;
	}

	public void setTuiles(ArrayList<Tuile> tuiles) {
		this.tuiles = tuiles;
	}

	public int getTuileRand1() {
		return tuileRand1;
	}

	public void setTuileRand1(int tuileRand1) {
		this.tuileRand1 = tuileRand1;
	}

	public int getTuileRand2() {
		return tuileRand2;
	}

	public void setTuileRand2(int tuileRand2) {
		this.tuileRand2 = tuileRand2;
	}

	public Tuile getTmp() {
		return tmp;
	}

	public void setTmp(Tuile tmp) {
		this.tmp = tmp;
	}
	
}

package fr.univ.artois;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import java.util.ArrayList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Jeu extends JFrame{
	
	private FenetreJeu panJeu = new FenetreJeu();
	private AideDeJeu aide = new AideDeJeu();
	private Introduction intro = new Introduction();
	
	//Fenêtre de victoire
	private FenetreFinDePartie victory = null;

	//Fenêtre de défaite
	private FenetreEchecDePartie defeat = null;
	
	public Jeu(){

		this.setTitle("2048");
		this.setPreferredSize(new Dimension(600, 600));
		this.pack();
		
		//Génération du jeu
		initJeu();
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
	}
	
	/**
	 * Ajoute les écouteurs à la fenêtre de jeu et celle d'aide puis définit la fenêtre de jeu comme étant le content pane courant
	 */
	public void initJeu(){
		
		//Ecouteur pour la fenêtre d'aide
		this.aide.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
					System.exit(0);
				}
				else if (e.getKeyCode() == KeyEvent.VK_H){
					Jeu.this.changementVersJeu();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		//Ajout de l'écouteur pour la fenêtre de jeu
		this.panJeu.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
					System.exit(0);
				}
				else if (e.getKeyCode() == KeyEvent.VK_H){
					Jeu.this.changementVersAide();
				}
				else if (e.getKeyCode() == KeyEvent.VK_J){
					Jeu.this.dispose();
					new Jeu();
				}
				else if (e.getKeyCode() == KeyEvent.VK_UP){
					jeu(2);
				}
				else if (e.getKeyCode() == KeyEvent.VK_RIGHT){
					jeu(3);
				}
				else if (e.getKeyCode() == KeyEvent.VK_DOWN){
					jeu(0);
				}
				else if (e.getKeyCode() == KeyEvent.VK_LEFT){
					jeu(1);
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		//Ajout de l'écouteur pour le panel d'introduction (permettant de choisir si le joueur joue ou une des deux IA
		this.intro.getJoueur().addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				Jeu.this.removeAll();
				Jeu.this.panJeu.setFocusable(true);
				Jeu.this.add(Jeu.this.panJeu);
				Jeu.this.pack();
				Jeu.this.revalidate();
				Jeu.this.repaint();
			}
		});
		
		this.intro.getIadebile().addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				Jeu.this.getContentPane().removeAll();
				Jeu.this.getContentPane().add(Jeu.this.panJeu);
				Jeu.this.panJeu.setFocusable(true);
				Jeu.this.pack();
				Jeu.this.revalidate();
				Jeu.this.repaint();
				int var;
				boolean tmp = false;
				while (tmp == false){
				try{
					Thread.sleep(100);
					var = Jeu.this.panJeu.getGt().getR().nextInt(4);
					jeu(var);
					tmp = Jeu.this.isDefeated();
				}
				catch (Exception ex){
					ex.printStackTrace();
					}
				}
			}
		});
		
		this.intro.getIaok().addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				/*
				 * Appel de la méthode qui va éclairer la grille et appeller la fonction jeu() avec les
				 * directions les plus appropriées
				 */
			}
		});
		
		this.panJeu.setFocusable(true);
		this.getContentPane().add(this.panJeu);
		this.pack();
		this.validate();
		this.repaint();
		
	}
	
	/**
	 * Méthode utilisée pour changer le content pane courant du jeu vers l'aide
	 */
	public void changementVersAide(){
		this.getContentPane().removeAll();
		this.invalidate();
		this.aide.setFocusable(true);
		this.getContentPane().add(this.aide);
		this.revalidate();
		this.repaint();
	}
	
	/**
	 * Méthode utilisée pour changer le content pane courant de l'aide vers le jeu
	 */
	public void changementVersJeu(){
		this.getContentPane().removeAll();
		this.invalidate();
		this.panJeu.setFocusable(true);
		this.getContentPane().add(this.panJeu);
		this.revalidate();
		this.repaint();
	}
	
	/**
	 * Cette méthode est appellée lorsqu'une des flêches est préssée.
	 * La méthode récupére quatre lignes de boutons et réalise les vérifications
	 * nécessaires pour fusionner, bouger et générer les tuiles.
	 * @param direction
	 */
	public void jeu(int direction){
		int ligne1[] = {-1,-1,-1,-1};
		int ligne2[] = {-1,-1,-1,-1};
		int ligne3[] = {-1,-1,-1,-1};
		int ligne4[] = {-1,-1,-1,-1};
		ArrayList<Tuile> lig1 = new ArrayList<Tuile>();
		ArrayList<Tuile> lig2 = new ArrayList<Tuile>();
		ArrayList<Tuile> lig3 = new ArrayList<Tuile>();
		ArrayList<Tuile> lig4 = new ArrayList<Tuile>();
		Tuile tmp = null;
		ArrayList<Tuile> nouvelleTuiles = new ArrayList<Tuile>();
		int j = 0, k = 0, l = 0, m = 0, newCase1;
		boolean flagRand = true;
		boolean flagMovement = false;
		boolean isVictorious = false;
		int cptTaille = 0;
		
		//Bas
		if (direction == 0){
			
			if (this.isDefeated() == true){
				this.panJeu.setFocusable(false);
				ajoutEcouteurDefaite();
				this.defeat.setFocusable(true);
				this.setContentPane(this.defeat);
				this.repaint();
				this.revalidate();
			}
			
			//Récupération des tuiles
			for(int i = 0; i < 16; ++i){
				tmp = this.panJeu.getGt().getTuiles().get(i);
				if (i == 0 || i == 4 || i == 8 || i == 12){
					lig1.add(tmp);
					ligne1[j++] = tmp.getValue();
				}
				else if (i == 1 || i == 5 || i == 9 || i == 13){
					lig2.add(tmp);
					ligne2[k++] = tmp.getValue();
				}
				else if (i == 2 || i == 6 || i == 10 || i == 14){
					lig3.add(tmp);
					ligne3[l++] = tmp.getValue();
				}
				else{
					lig4.add(tmp);
					ligne4[m++] = tmp.getValue();
				}
			}
			
			/*
			 * Inspection des alentours de chaque lignes pour pouvoir faire les fusions ainsi que générer une
			 * tuile aléatoire supplémentaire
			 */
			
			//Simple mouvement des tuiles
			for(int a = 0; a < 3; ++a){
				j = 0;
				k = 0;
				l = 0;
				m = 0;
				for(int i = 0; i < 16; ++i){
					//j
					if (j < 3){
						if (ligne1[j] != -1 && ligne1[j+1] == -1){
							lig1.get(j+1).setValue(lig1.get(j).getValue());
							ligne1[j+1] = lig1.get(j).getValue();
							ligne1[j] = -1;
							lig1.get(j).setValue(-1);
							++j;
							flagMovement = true;
						}
						else{
							++j;
						}
					}
					
					//k
					else if (k < 3){
						if (ligne2[k] != -1 && ligne2[k+1] == -1){
							lig2.get(k+1).setValue(lig2.get(k).getValue());
							ligne2[k+1] = lig2.get(k).getValue();
							ligne2[k] = -1;
							lig2.get(k).setValue(-1);
							++k;
							flagMovement = true;
						}
						else{
							++k;
						}
					}
					
					//l
					else if (l < 3){
						if (ligne3[l] != -1 && ligne3[l+1] == -1){
							lig3.get(l+1).setValue(lig3.get(l).getValue());
							ligne3[l+1] = lig3.get(l).getValue();
							ligne3[l] = -1;
							lig3.get(l).setValue(-1);
							++l;
							flagMovement = true;
						}
						else{
							++l;
						}
					}
					
					//m
					else if (m < 3){
						if (ligne4[m] != -1 && ligne4[m+1] == -1){
							lig4.get(m+1).setValue(lig4.get(m).getValue());
							ligne4[m+1] = lig4.get(m).getValue();
							ligne4[m] = -1;
							lig4.get(m).setValue(-1);
							++m;
							flagMovement = true;
						}
						else{
							++m;
						}
					}
				}
			}
			
			j = 0;
			k = 0;
			l = 0;
			m = 0;
			
			//Fusion des tuiles
			for(int i = 1; i < 16; ++i){
				if (j < 3){
					if (ligne1[j] != -1 && ligne1[j] == ligne1[j+1]){
						ligne1[j+1] = ligne1[j] + ligne1[j+1];
						ligne1[j] = -1;
						lig1.get(j+1).setValue(ligne1[j+1]);
						lig1.get(j).setValue(-1);
						this.panJeu.getGt().setScore(this.panJeu.getGt().getScore() + ligne1[j+1]);
						++j;
						flagMovement = true;
					}
					else{
						++j;
					}
				}
				else if (k < 3){
					if (ligne2[k] != -1 && ligne2[k] == ligne2[k+1]){
						ligne2[k+1] = ligne2[k] + ligne2[k+1];
						ligne2[k] = -1;
						lig2.get(k+1).setValue(ligne2[k+1]);
						lig2.get(k).setValue(-1);
						this.panJeu.getGt().setScore(this.panJeu.getGt().getScore() + ligne2[k+1]);
						++k;
						flagMovement = true;
					}
					else{
						++k;
					}
				}
				else if (l < 3){
					if (ligne3[l] != -1 && ligne3[l] == ligne3[l+1]){
						ligne3[l+1] = ligne3[l] + ligne3[l+1];
						ligne3[l] = -1;
						lig3.get(l+1).setValue(ligne3[l+1]);
						lig3.get(l).setValue(-1);
						this.panJeu.getGt().setScore(this.panJeu.getGt().getScore() + ligne3[l+1]);
						++l;
						flagMovement = true;
					}
					else{
						++l;
					}
				}
				else if (m < 3){
					if (ligne4[m] != -1 && ligne4[m] == ligne4[m+1]){
						ligne4[m+1] = ligne4[m] + ligne4[m+1];
						ligne4[m] = -1;
						lig4.get(m+1).setValue(ligne4[m+1]);
						lig4.get(m).setValue(-1);
						this.panJeu.getGt().setScore(this.panJeu.getGt().getScore() + ligne4[m+1]);
						++m;
						flagMovement = true;
					}
					else{
						++m;
					}
				}
			}
			
			this.panJeu.getGt().removeAll();
			
			for(Tuile tuile : lig1){
				if (tuile.getValue() == -1){
					++cptTaille;
					break;
				}
			}
			
			for(Tuile tuile: lig2){
				if (tuile.getValue() == -1){
					++cptTaille;
					break;
				}
			}
			
			for(Tuile tuile: lig3){
				if (tuile.getValue() == -1){
					++cptTaille;
					break;
				}
			}
			
			for(Tuile tuile: lig4){
				if (tuile.getValue() == -1){
					++cptTaille;
					break;
				}
			}
			
			
			if (cptTaille > 0 && flagMovement){
				//Génération d'une nouvelle tuile aléatoire
				do{
					newCase1 = this.panJeu.getGt().getR().nextInt(16);
					if (newCase1 == 0 || newCase1 == 4 || newCase1 == 8 || newCase1 == 12){
						if (newCase1 == 4) newCase1 = 1;
						else if (newCase1 == 8) newCase1 = 2;
						else if (newCase1 == 12) newCase1 = 3;
						if (lig1.get(newCase1).getValue() == -1){
							flagRand = false;
							lig1.get(newCase1).setValue(2);
							ligne1[newCase1] = 2;
						}
					}
					else if (newCase1 == 1 || newCase1 == 5 || newCase1 == 9 || newCase1 == 13){
						if (newCase1 == 1) newCase1 = 0;
						else if (newCase1 == 5) newCase1 = 1;
						else if (newCase1 == 9) newCase1 = 2;
						else newCase1 = 3;
						if (lig2.get(newCase1).getValue() == -1){
							flagRand = false;
							lig2.get(newCase1).setValue(2);
							ligne2[newCase1] = 2;
						}
					}
					else if (newCase1 == 2 || newCase1 == 6 || newCase1 == 10 || newCase1 == 14){
						if (newCase1 == 2) newCase1 = 0;
						else if (newCase1 == 6) newCase1 = 1;
						else if (newCase1 == 10) newCase1 = 2;
						else newCase1 = 3;
						if (lig3.get(newCase1).getValue() == -1){
							flagRand = false;
							lig3.get(newCase1).setValue(2);
							ligne3[newCase1] = 2;
						}
					}
					else{
						if (newCase1 == 3) newCase1 = 0;
						else if (newCase1 == 7) newCase1 = 1;
						else if (newCase1 == 11) newCase1 = 2;
						else if (newCase1 == 15) newCase1 = 3;
						if (lig4.get(newCase1).getValue() == -1){
							flagRand = false;
							lig4.get(newCase1).setValue(2);
							ligne4[newCase1] = 2;
						}
					}
					
				}while(flagRand);
			}
			
			j = 0;
			
			this.panJeu.getGt().setTuiles(nouvelleTuiles);
			
			for(int i = 0; i < 16; ++i){
				if (i == 0 || i == 4 || i == 8 || i == 12){
					j = i;
					if (j == 4) j = 1;
					else if (j == 8) j = 2;
					else if (j == 12) j = 3;
					this.panJeu.getGt().add(lig1.get(j));
					this.panJeu.getGt().getTuiles().add(lig1.get(j));
				}
				else if (i == 1 || i == 5 || i == 9 || i == 13){
					j = i;
					if (j == 1) j = 0;
					else if (j == 5) j = 1;
					else if (j == 9) j = 2;
					else j = 3;
					this.panJeu.getGt().add(lig2.get(j));
					this.panJeu.getGt().getTuiles().add(lig2.get(j));
				}
				else if (i == 2 || i == 6 || i == 10 || i == 14){
					j = i;
					if (j == 2) j = 0;
					else if (j == 6) j = 1;
					else if (j == 10) j = 2;
					else j = 3;
					this.panJeu.getGt().add(lig3.get(j));
					this.panJeu.getGt().getTuiles().add(lig3.get(j));
				}
				else {
					j = i;
					if (j == 3) j = 0;
					else if (j == 7) j = 1;
					else if (j == 11) j = 2;
					else if (j == 15) j = 3;
					this.panJeu.getGt().add(lig4.get(j));
					this.panJeu.getGt().getTuiles().add(lig4.get(j));
				}
			}
			
			for(Tuile tuile : this.panJeu.getGt().getTuiles()){
				if (tuile.getValue() == 2048){
					isVictorious = true;
				}
			}
			
			if (isVictorious){
				this.panJeu.setFocusable(false);
				ajoutEcouteurVictoire();
				this.setContentPane(this.victory);
				this.victory.setFocusable(true);
				this.repaint();
				this.revalidate();
			}
			else{
				this.panJeu.getGt().repaint();
				this.panJeu.repaint();
			}
		}
		
		//Gauche
		else if (direction == 1){
			
			if (this.isDefeated() == true){
				this.panJeu.setFocusable(false);
				ajoutEcouteurDefaite();
				this.defeat.setFocusable(true);
				this.setContentPane(this.defeat);
				this.repaint();
				this.revalidate();
			}
			
			//Je récupére les différentes lignes de tuiles
			for(int i = 0; i < 16; ++i){
				tmp = this.panJeu.getGt().getTuiles().get(i);
				if (i < 4){
					lig1.add(tmp);
					ligne1[j++] = tmp.getValue();
				}
				else if (i >= 4 && i < 8){
					lig2.add(tmp);
					ligne2[k++] = tmp.getValue();
				}
				else if (i >= 8 && i < 12){
					lig3.add(tmp);
					ligne3[l++] = tmp.getValue();
				}
				else{
					lig4.add(tmp);
					ligne4[m++] = tmp.getValue();
				}
			}
			
			/*
			 * Inspection des alentours de chaque lignes pour pouvoir faire les fusions ainsi que générer une
			 * tuile aléatoire supplémentaire
			 */
			
			//Simple mouvement des tuiles
			for(int a = 0; a < 3; ++a){
				j = 1;
				k = 1;
				l = 1;
				m = 1;
				for(int i = 0; i < 16; ++i){
					//j
					if (j < 4){
						if (ligne1[j] != -1 && ligne1[j-1] == -1){
							lig1.get(j-1).setValue(lig1.get(j).getValue());
							ligne1[j-1] = lig1.get(j).getValue();
							ligne1[j] = -1;
							lig1.get(j).setValue(-1);
							++j;
							flagMovement = true;
						}
						else{
							++j;
						}
					}
					
					//k
					else if (k < 4){
						if (ligne2[k] != -1 && ligne2[k-1] == -1){
							lig2.get(k-1).setValue(lig2.get(k).getValue());
							ligne2[k-1] = lig2.get(k).getValue();
							ligne2[k] = -1;
							lig2.get(k).setValue(-1);
							++k;
							flagMovement = true;
						}
						else{
							++k;
						}
					}
					
					//l
					else if (l < 4){
						if (ligne3[l] != -1 && ligne3[l-1] == -1){
							lig3.get(l-1).setValue(lig3.get(l).getValue());
							ligne3[l-1] = lig3.get(l).getValue();
							ligne3[l] = -1;
							lig3.get(l).setValue(-1);
							++l;
							flagMovement = true;
						}
						else{
							++l;
						}
					}
					
					//m
					else if (m < 4){
						if (ligne4[m] != -1 && ligne4[m-1] == -1){
							lig4.get(m-1).setValue(lig4.get(m).getValue());
							ligne4[m-1] = lig4.get(m).getValue();
							ligne4[m] = -1;
							lig4.get(m).setValue(-1);
							++m;
							flagMovement = true;
						}
						else{
							++m;
						}
					}
				}
			}
			
			j = 1;
			k = 1;
			l = 1;
			m = 1;
			
			//Fusion des tuiles
			for(int i = 1; i < 16; ++i){
				if (j < 4){
					if (ligne1[j] != -1 && ligne1[j] == ligne1[j-1]){
						ligne1[j-1] = ligne1[j] + ligne1[j-1];
						ligne1[j] = -1;
						lig1.get(j-1).setValue(ligne1[j-1]);
						lig1.get(j).setValue(-1);
						this.panJeu.getGt().setScore(this.panJeu.getGt().getScore() + ligne1[j-1]);
						++j;
						flagMovement = true;
					}
					else{
						++j;
					}
				}
				else if (k < 4){
					if (ligne2[k] != -1 && ligne2[k] == ligne2[k-1]){
						ligne2[k-1] = ligne2[k] + ligne2[k-1];
						ligne2[k] = -1;
						lig2.get(k-1).setValue(ligne2[k-1]);
						lig2.get(k).setValue(-1);
						this.panJeu.getGt().setScore(this.panJeu.getGt().getScore() + ligne2[k-1]);
						++k;
						flagMovement = true;
					}
					else{
						++k;
					}
				}
				else if (l < 4){
					if (ligne3[l] != -1 && ligne3[l] == ligne3[l-1]){
						ligne3[l-1] = ligne3[l] + ligne3[l-1];
						ligne3[l] = -1;
						lig3.get(l-1).setValue(ligne3[l-1]);
						lig3.get(l).setValue(-1);
						this.panJeu.getGt().setScore(this.panJeu.getGt().getScore() + ligne3[l-1]);
						++l;
						flagMovement = true;
					}
					else{
						++l;
					}
				}
				else if (m < 4){
					if (ligne4[m] != -1 && ligne4[m] == ligne4[m-1]){
						ligne4[m-1] = ligne4[m] + ligne4[m-1];
						ligne4[m] = -1;
						lig4.get(m-1).setValue(ligne4[m-1]);
						lig4.get(m).setValue(-1);
						this.panJeu.getGt().setScore(this.panJeu.getGt().getScore() + ligne4[m-1]);
						++m;
						flagMovement = true;
					}
					else{
						++m;
					}
				}
			}
			
			this.panJeu.getGt().removeAll();
			
			for(Tuile tuile : lig1){
				if (tuile.getValue() == -1){
					++cptTaille;
					break;
				}
			}
			
			for(Tuile tuile: lig2){
				if (tuile.getValue() == -1){
					++cptTaille;
					break;
				}
			}
			
			for(Tuile tuile: lig3){
				if (tuile.getValue() == -1){
					++cptTaille;
					break;
				}
			}
			
			for(Tuile tuile: lig4){
				if (tuile.getValue() == -1){
					++cptTaille;
					break;
				}
			}
			
			
			if (cptTaille > 0 && flagMovement){
				//Génération d'une nouvelle tuile aléatoire
				do{
					newCase1 = this.panJeu.getGt().getR().nextInt(16);
					if (newCase1 < 4){
						if (lig1.get(newCase1).getValue() == -1){
							flagRand = false;
							lig1.get(newCase1).setValue(2);
							ligne1[newCase1] = 2;
						}
					}
					else if (newCase1 < 8){
						newCase1 -= 4;
						if (lig2.get(newCase1).getValue() == -1){
							flagRand = false;
							lig2.get(newCase1).setValue(2);
							ligne2[newCase1] = 2;
						}
					}
					else if (newCase1 < 12){
						newCase1 -= 8;
						if (lig3.get(newCase1).getValue() == -1){
							flagRand = false;
							lig3.get(newCase1).setValue(2);
							ligne3[newCase1] = 2;
						}
					}
					else{
						newCase1 -= 12;
						if (lig4.get(newCase1).getValue() == -1){
							flagRand = false;
							lig4.get(newCase1).setValue(2);
							ligne4[newCase1] = 2;
						}
					}
					
				}while(flagRand);
			}
			
			
			j = 0;
			k = 0;
			l = 0;
			m = 0;
			
			this.panJeu.getGt().setTuiles(nouvelleTuiles);
			
			for(int i = 0; i < 16; ++i){
				if (j < 4){
					this.panJeu.getGt().add(lig1.get(j));
					this.panJeu.getGt().getTuiles().add(lig1.get(j));
					++j;
				}
				else if (k < 4){
					this.panJeu.getGt().add(lig2.get(k));
					this.panJeu.getGt().getTuiles().add(lig2.get(k));
					++k;
				}
				else if (l < 4){
					this.panJeu.getGt().add(lig3.get(l));
					this.panJeu.getGt().getTuiles().add(lig3.get(l));
					++l;
				}
				else if (m < 4){
					this.panJeu.getGt().add(lig4.get(m));
					this.panJeu.getGt().getTuiles().add(lig4.get(m));
					++m;
				}
			}
			
			for(Tuile tuile : this.panJeu.getGt().getTuiles()){
				if (tuile.getValue() == 2048){
					isVictorious = true;
				}
			}
			
			if (isVictorious){
				this.panJeu.setFocusable(false);
				ajoutEcouteurVictoire();
				this.setContentPane(this.victory);
				this.victory.setFocusable(true);
				this.repaint();
				this.revalidate();
			}
			else{
				this.panJeu.getGt().repaint();
				this.panJeu.repaint();
			}
			
		}
		
		//Haut
		else if (direction == 2){
			
			if (this.isDefeated() == true){
				this.panJeu.setFocusable(false);
				ajoutEcouteurDefaite();
				this.defeat.setFocusable(true);
				this.setContentPane(this.defeat);
				this.repaint();
				this.revalidate();
			}
			
			//Récupération des tuiles
			for(int i = 0; i < 16; ++i){
				tmp = this.panJeu.getGt().getTuiles().get(i);
				if (i == 0 || i == 4 || i == 8 || i == 12){
					lig1.add(tmp);
					ligne1[j++] = tmp.getValue();
				}
				else if (i == 1 || i == 5 || i == 9 || i == 13){
					lig2.add(tmp);
					ligne2[k++] = tmp.getValue();
				}
				else if (i == 2 || i == 6 || i == 10 || i == 14){
					lig3.add(tmp);
					ligne3[l++] = tmp.getValue();
				}
				else{
					lig4.add(tmp);
					ligne4[m++] = tmp.getValue();
				}
			}
			
			/*
			 * Inspection des alentours de chaque lignes pour pouvoir faire les fusions ainsi que générer une
			 * tuile aléatoire supplémentaire
			 */
			
			//Simple mouvement des tuiles
			for(int a = 0; a < 3; ++a){
				j = 1;
				k = 1;
				l = 1;
				m = 1;
				for(int i = 0; i < 16; ++i){
					//j
					if (j < 4){
						if (ligne1[j] != -1 && ligne1[j+-1] == -1){
							lig1.get(j-1).setValue(lig1.get(j).getValue());
							ligne1[j-1] = lig1.get(j).getValue();
							ligne1[j] = -1;
							lig1.get(j).setValue(-1);
							++j;
							flagMovement = true;
						}
						else{
							++j;
						}
					}
					
					//k
					else if (k < 4){
						if (ligne2[k] != -1 && ligne2[k-1] == -1){
							lig2.get(k-1).setValue(lig2.get(k).getValue());
							ligne2[k-1] = lig2.get(k).getValue();
							ligne2[k] = -1;
							lig2.get(k).setValue(-1);
							++k;
							flagMovement = true;
						}
						else{
							++k;
						}
					}
					
					//l
					else if (l < 4){
						if (ligne3[l] != -1 && ligne3[l-1] == -1){
							lig3.get(l-1).setValue(lig3.get(l).getValue());
							ligne3[l-1] = lig3.get(l).getValue();
							ligne3[l] = -1;
							lig3.get(l).setValue(-1);
							++l;
							flagMovement = true;
						}
						else{
							++l;
						}
					}
					
					//m
					else if (m < 4){
						if (ligne4[m] != -1 && ligne4[m-1] == -1){
							lig4.get(m-1).setValue(lig4.get(m).getValue());
							ligne4[m-1] = lig4.get(m).getValue();
							ligne4[m] = -1;
							lig4.get(m).setValue(-1);
							++m;
							flagMovement = true;
						}
						else{
							++m;
						}
					}
				}
			}
			
			j = 1;
			k = 1;
			l = 1;
			m = 1;
			
			//Fusion des tuiles
			for(int i = 1; i < 16; ++i){
				if (j < 4){
					if (ligne1[j] != -1 && ligne1[j] == ligne1[j-1]){
						ligne1[j-1] = ligne1[j] + ligne1[j-1];
						ligne1[j] = -1;
						lig1.get(j-1).setValue(ligne1[j-1]);
						lig1.get(j).setValue(-1);
						this.panJeu.getGt().setScore(this.panJeu.getGt().getScore() + ligne1[j-1]);
						++j;
						flagMovement = true;
					}
					else{
						++j;
					}
				}
				else if (k < 4){
					if (ligne2[k] != -1 && ligne2[k] == ligne2[k-1]){
						ligne2[k-1] = ligne2[k] + ligne2[k-1];
						ligne2[k] = -1;
						lig2.get(k-1).setValue(ligne2[k-1]);
						lig2.get(k).setValue(-1);
						this.panJeu.getGt().setScore(this.panJeu.getGt().getScore() + ligne2[k-1]);
						++k;
						flagMovement = true;
					}
					else{
						++k;
					}
				}
				else if (l < 4){
					if (ligne3[l] != -1 && ligne3[l] == ligne3[l-1]){
						ligne3[l-1] = ligne3[l] + ligne3[l-1];
						ligne3[l] = -1;
						lig3.get(l-1).setValue(ligne3[l-1]);
						lig3.get(l).setValue(-1);
						this.panJeu.getGt().setScore(this.panJeu.getGt().getScore() + ligne3[l-1]);
						++l;
						flagMovement = true;
					}
					else{
						++l;
					}
				}
				else if (m < 4){
					if (ligne4[m] != -1 && ligne4[m] == ligne4[m-1]){
						ligne4[m-1] = ligne4[m] + ligne4[m-1];
						ligne4[m] = -1;
						lig4.get(m-1).setValue(ligne4[m-1]);
						lig4.get(m).setValue(-1);
						this.panJeu.getGt().setScore(this.panJeu.getGt().getScore() + ligne4[m-1]);
						++m;
						flagMovement = true;
					}
					else{
						++m;
					}
				}
			}
			
			this.panJeu.getGt().removeAll();
			
			for(Tuile tuile : lig1){
				if (tuile.getValue() == -1){
					++cptTaille;
					break;
				}
			}
			
			for(Tuile tuile: lig2){
				if (tuile.getValue() == -1){
					++cptTaille;
					break;
				}
			}
			
			for(Tuile tuile: lig3){
				if (tuile.getValue() == -1){
					++cptTaille;
					break;
				}
			}
			
			for(Tuile tuile: lig4){
				if (tuile.getValue() == -1){
					++cptTaille;
					break;
				}
			}
			
			
			if (cptTaille > 0 && flagMovement){
				//Génération d'une nouvelle tuile aléatoire
				do{
					newCase1 = this.panJeu.getGt().getR().nextInt(16);
					if (newCase1 == 0 || newCase1 == 4 || newCase1 == 8 || newCase1 == 12){
						if (newCase1 == 4) newCase1 = 1;
						else if (newCase1 == 8) newCase1 = 2;
						else if (newCase1 == 12) newCase1 = 3;
						if (lig1.get(newCase1).getValue() == -1){
							flagRand = false;
							lig1.get(newCase1).setValue(2);
							ligne1[newCase1] = 2;
						}
					}
					else if (newCase1 == 1 || newCase1 == 5 || newCase1 == 9 || newCase1 == 13){
						if (newCase1 == 1) newCase1 = 0;
						else if (newCase1 == 5) newCase1 = 1;
						else if (newCase1 == 9) newCase1 = 2;
						else newCase1 = 3;
						if (lig2.get(newCase1).getValue() == -1){
							flagRand = false;
							lig2.get(newCase1).setValue(2);
							ligne2[newCase1] = 2;
						}
					}
					else if (newCase1 == 2 || newCase1 == 6 || newCase1 == 10 || newCase1 == 14){
						if (newCase1 == 2) newCase1 = 0;
						else if (newCase1 == 6) newCase1 = 1;
						else if (newCase1 == 10) newCase1 = 2;
						else newCase1 = 3;
						if (lig3.get(newCase1).getValue() == -1){
							flagRand = false;
							lig3.get(newCase1).setValue(2);
							ligne3[newCase1] = 2;
						}
					}
					else{
						if (newCase1 == 3) newCase1 = 0;
						else if (newCase1 == 7) newCase1 = 1;
						else if (newCase1 == 11) newCase1 = 2;
						else if (newCase1 == 15) newCase1 = 3;
						if (lig4.get(newCase1).getValue() == -1){
							flagRand = false;
							lig4.get(newCase1).setValue(2);
							ligne4[newCase1] = 2;
						}
					}
					
				}while(flagRand);
			}
			
			j = 0;
			
			this.panJeu.getGt().setTuiles(nouvelleTuiles);
			
			for(int i = 0; i < 16; ++i){
				if (i == 0 || i == 4 || i == 8 || i == 12){
					j = i;
					if (j == 4) j = 1;
					else if (j == 8) j = 2;
					else if (j == 12) j = 3;
					this.panJeu.getGt().add(lig1.get(j));
					this.panJeu.getGt().getTuiles().add(lig1.get(j));
				}
				else if (i == 1 || i == 5 || i == 9 || i == 13){
					j = i;
					if (j == 1) j = 0;
					else if (j == 5) j = 1;
					else if (j == 9) j = 2;
					else j = 3;
					this.panJeu.getGt().add(lig2.get(j));
					this.panJeu.getGt().getTuiles().add(lig2.get(j));
				}
				else if (i == 2 || i == 6 || i == 10 || i == 14){
					j = i;
					if (j == 2) j = 0;
					else if (j == 6) j = 1;
					else if (j == 10) j = 2;
					else j = 3;
					this.panJeu.getGt().add(lig3.get(j));
					this.panJeu.getGt().getTuiles().add(lig3.get(j));
				}
				else {
					j = i;
					if (j == 3) j = 0;
					else if (j == 7) j = 1;
					else if (j == 11) j = 2;
					else if (j == 15) j = 3;
					this.panJeu.getGt().add(lig4.get(j));
					this.panJeu.getGt().getTuiles().add(lig4.get(j));
				}
			}
			
			for(Tuile tuile : this.panJeu.getGt().getTuiles()){
				if (tuile.getValue() == 2048){
					isVictorious = true;
				}
			}
			
			if (isVictorious){
				this.panJeu.setFocusable(false);
				ajoutEcouteurVictoire();
				this.setContentPane(this.victory);
				this.victory.setFocusable(true);
				this.repaint();
				this.revalidate();
			}
			else{
				this.panJeu.getGt().repaint();
				this.panJeu.repaint();
			}
			
		}
		
		//Droite
		else{
			
			if (this.isDefeated() == true){
				this.panJeu.setFocusable(false);
				ajoutEcouteurDefaite();
				this.defeat.setFocusable(true);
				this.setContentPane(this.defeat);
				this.repaint();
				this.revalidate();
			}
			
			for(int i = 0; i < 16; ++i){
				tmp = this.panJeu.getGt().getTuiles().get(i);
				if (i < 4){
					lig1.add(tmp);
					ligne1[j++] = tmp.getValue();
				}
				else if (i >= 4 && i < 8){
					lig2.add(tmp);
					ligne2[k++] = tmp.getValue();
				}
				else if (i >= 8 && i < 12){
					lig3.add(tmp);
					ligne3[l++] = tmp.getValue();
				}
				else{
					lig4.add(tmp);
					ligne4[m++] = tmp.getValue();
				}
			}
			
			/*
			 * Inspection des alentours de chaque lignes pour pouvoir faire les fusions ainsi que générer une
			 * tuile aléatoire supplémentaire
			 */
			
			//Simple mouvement des tuiles
			for(int a = 0; a < 3; ++a){
				j = 0;
				k = 0;
				l = 0;
				m = 0;
				for(int i = 0; i < 16; ++i){
					//j
					if (j < 3){
						if (ligne1[j] != -1 && ligne1[j+1] == -1){
							lig1.get(j+1).setValue(lig1.get(j).getValue());
							ligne1[j+1] = lig1.get(j).getValue();
							ligne1[j] = -1;
							lig1.get(j).setValue(-1);
							++j;
							flagMovement = true;
						}
						else{
							++j;
						}
					}
					
					//k
					else if (k < 3){
						if (ligne2[k] != -1 && ligne2[k+1] == -1){
							lig2.get(k+1).setValue(lig2.get(k).getValue());
							ligne2[k+1] = lig2.get(k).getValue();
							ligne2[k] = -1;
							lig2.get(k).setValue(-1);
							++k;
							flagMovement = true;
						}
						else{
							++k;
						}
					}
					
					//l
					else if (l < 3){
						if (ligne3[l] != -1 && ligne3[l+1] == -1){
							lig3.get(l+1).setValue(lig3.get(l).getValue());
							ligne3[l+1] = lig3.get(l).getValue();
							ligne3[l] = -1;
							lig3.get(l).setValue(-1);
							++l;
							flagMovement = true;
						}
						else{
							++l;
						}
					}
					
					//m
					else if (m < 3){
						if (ligne4[m] != -1 && ligne4[m+1] == -1){
							lig4.get(m+1).setValue(lig4.get(m).getValue());
							ligne4[m+1] = lig4.get(m).getValue();
							ligne4[m] = -1;
							lig4.get(m).setValue(-1);
							++m;
							flagMovement = true;
						}
						else{
							++m;
						}
					}
				}
			}
			
			j = 0;
			k = 0;
			l = 0;
			m = 0;
			
			//Fusion des tuiles
			for(int i = 1; i < 16; ++i){
				if (j < 3){
					if (ligne1[j] != -1 && ligne1[j] == ligne1[j+1]){
						ligne1[j+1] = ligne1[j] + ligne1[j+1];
						ligne1[j] = -1;
						lig1.get(j+1).setValue(ligne1[j+1]);
						lig1.get(j).setValue(-1);
						this.panJeu.getGt().setScore(this.panJeu.getGt().getScore() + ligne1[j+1]);
						++j;
						flagMovement = true;
					}
					else{
						++j;
					}
				}
				else if (k < 3){
					if (ligne2[k] != -1 && ligne2[k] == ligne2[k+1]){
						ligne2[k+1] = ligne2[k] + ligne2[k+1];
						ligne2[k] = -1;
						lig2.get(k+1).setValue(ligne2[k+1]);
						lig2.get(k).setValue(-1);
						this.panJeu.getGt().setScore(this.panJeu.getGt().getScore() + ligne2[k+1]);
						++k;
						flagMovement = true;
					}
					else{
						++k;
					}
				}
				else if (l < 3){
					if (ligne3[l] != -1 && ligne3[l] == ligne3[l+1]){
						ligne3[l+1] = ligne3[l] + ligne3[l+1];
						ligne3[l] = -1;
						lig3.get(l+1).setValue(ligne3[l+1]);
						lig3.get(l).setValue(-1);
						this.panJeu.getGt().setScore(this.panJeu.getGt().getScore() + ligne3[l+1]);
						++l;
						flagMovement = true;
					}
					else{
						++l;
					}
				}
				else if (m < 3){
					if (ligne4[m] != -1 && ligne4[m] == ligne4[m+1]){
						ligne4[m+1] = ligne4[m] + ligne4[m+1];
						ligne4[m] = -1;
						lig4.get(m+1).setValue(ligne4[m+1]);
						lig4.get(m).setValue(-1);
						this.panJeu.getGt().setScore(this.panJeu.getGt().getScore() + ligne4[m+1]);
						++m;
						flagMovement = true;
					}
					else{
						++m;
					}
				}
			}
			
			this.panJeu.getGt().removeAll();
			
			for(Tuile tuile : lig1){
				if (tuile.getValue() == -1){
					++cptTaille;
					break;
				}
			}
			
			for(Tuile tuile: lig2){
				if (tuile.getValue() == -1){
					++cptTaille;
					break;
				}
			}
			
			for(Tuile tuile: lig3){
				if (tuile.getValue() == -1){
					++cptTaille;
					break;
				}
			}
			
			for(Tuile tuile: lig4){
				if (tuile.getValue() == -1){
					++cptTaille;
					break;
				}
			}
			
			
			if (cptTaille > 0 && flagMovement){
				//Génération d'une nouvelle tuile aléatoire
				do{
					newCase1 = this.panJeu.getGt().getR().nextInt(16);
					if (newCase1 < 4){
						if (lig1.get(newCase1).getValue() == -1){
							flagRand = false;
							lig1.get(newCase1).setValue(2);
							ligne1[newCase1] = 2;
						}
					}
					else if (newCase1 < 8){
						newCase1 -= 4;
						if (lig2.get(newCase1).getValue() == -1){
							flagRand = false;
							lig2.get(newCase1).setValue(2);
							ligne2[newCase1] = 2;
						}
					}
					else if (newCase1 < 12){
						newCase1 -= 8;
						if (lig3.get(newCase1).getValue() == -1){
							flagRand = false;
							lig3.get(newCase1).setValue(2);
							ligne3[newCase1] = 2;
						}
					}
					else{
						newCase1 -= 12;
						if (lig4.get(newCase1).getValue() == -1){
							flagRand = false;
							lig4.get(newCase1).setValue(2);
							ligne4[newCase1] = 2;
						}
					}
					
				}while(flagRand);
			}
			
			
			j = 0;
			k = 0;
			l = 0;
			m = 0;
			
			this.panJeu.getGt().setTuiles(nouvelleTuiles);
			
			for(int i = 0; i < 16; ++i){
				if (j < 4){
					this.panJeu.getGt().add(lig1.get(j));
					this.panJeu.getGt().getTuiles().add(lig1.get(j));
					++j;
				}
				else if (k < 4){
					this.panJeu.getGt().add(lig2.get(k));
					this.panJeu.getGt().getTuiles().add(lig2.get(k));
					++k;
				}
				else if (l < 4){
					this.panJeu.getGt().add(lig3.get(l));
					this.panJeu.getGt().getTuiles().add(lig3.get(l));
					++l;
				}
				else if (m < 4){
					this.panJeu.getGt().add(lig4.get(m));
					this.panJeu.getGt().getTuiles().add(lig4.get(m));
					++m;
				}
			}
			
			for(Tuile tuile : this.panJeu.getGt().getTuiles()){
				if (tuile.getValue() == 2048){
					isVictorious = true;
				}
			}
			
			if (isVictorious){
				this.panJeu.setFocusable(false);
				ajoutEcouteurVictoire();
				this.setContentPane(this.victory);
				this.victory.setFocusable(true);
				this.repaint();
				this.revalidate();
			}
			else{
				this.panJeu.getGt().repaint();
				this.panJeu.repaint();
			}
			
		}
		
	}
	
	/**
	 * Méthode initialisant une nouvelle fenêtre de fin de partie (+ ajout d'un écouteur)
	 */
	public void ajoutEcouteurVictoire(){
		this.victory = new FenetreFinDePartie(this.panJeu.getGt().getScore());
		//Ecouteur de la victoire
		this.victory.addKeyListener(new KeyListener(){
			@Override
			public void keyPressed(KeyEvent e){
				System.out.println("Je suis ici");
				if (e.getKeyCode() == KeyEvent.VK_R){
					Jeu.this.dispose();
					new Jeu();
				}
				else if (e.getKeyCode() == KeyEvent.VK_C){
					
				}
				
			}

			@Override
			public void keyReleased(KeyEvent arg0){
				
				
			}

			@Override
			public void keyTyped(KeyEvent arg0){
				
				
			}
		});
	}
	
	/**
	 * Méthode initialisant une nouvelle fenêtre d'échec de partie (+ ajout d'un écouteur)
	 */
	public void ajoutEcouteurDefaite(){
		
		this.defeat = new FenetreEchecDePartie(this.panJeu.getGt().getScore());
		
		this.defeat.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_Q){
					System.exit(0);
				}
				else if (e.getKeyCode() == KeyEvent.VK_R){
					Jeu.this.dispose();
					new Jeu();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	
	/**
	 * Cette méthode permet de savoir si la grille est pleine et qu'aucune fusion n'est possible, ce qui aboutit à une défaite
	 * @return boolean
	 */
	public boolean isDefeated(){
		boolean isFull = true;
		
		for(int i = 0; i < this.panJeu.getGt().getTuiles().size() ; ++i){
			if (this.panJeu.getGt().getTuiles().get(i).getValue() == -1){
				isFull = false;
				break;
			}
		}
		
		if (isFull == false){
			return false;
		}
		else{
			for(int i = 0; i < this.panJeu.getGt().getTuiles().size() - 1; ++i){
				if (i == 3 || i == 7 || i == 11){
					if (this.panJeu.getGt().getTuiles().get(i) == this.panJeu.getGt().getTuiles().get(i+4)){
						return false;
					}
				}
				else if (i >= 12){
					if (this.panJeu.getGt().getTuiles().get(i) == this.panJeu.getGt().getTuiles().get(i+1)){
						return false;
					}
				}
				else{
					if (this.panJeu.getGt().getTuiles().get(i) == this.panJeu.getGt().getTuiles().get(i+1) || (this.panJeu.getGt().getTuiles().get(i) == this.panJeu.getGt().getTuiles().get(i+4))){
						return false;
					}
				}
			}
			return true;
		}
		
	}
	
	/**
	 * Cette méthode est appelée si une ia OK est choisie, la grille sera alors analysée 
	 * et iaOK envoie la direction la plus appropriée vers la méthode jeu(int)
	 */
	public void iaOK(){
		
		int random1 = 0, g = 0, b = 0;
		
		while(true){
			try{
				g = gaucheDroiteIA();
				b = hautBasIA();
				
				if (g > b){
					random1 = this.panJeu.getGt().getR().nextInt(1);
					if (random1 == 0){
						jeu(1);
					}
					else{
						jeu(3);
					}
				}
				else if (b > g){
					random1 = this.panJeu.getGt().getR().nextInt(1);
					if (random1 == 0){
						jeu(2);
					}
					else{
						jeu(0);
					}
				}
				else{
					jeu(this.panJeu.getGt().getR().nextInt(4));
				}
				
				Thread.sleep(200);
				
			}
			catch (Exception e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Cette méthode vérifie si des fusions sont possibles sur chaque ligne horizontale et retourne le nombre de fusions possibles
	 * @return int
	 */
	public int gaucheDroiteIA(){
		int cpt = 0;
		
		for(int i = 0; i < 16; ++i){
			if (i < 3){
				if (this.panJeu.getGt().getTuiles().get(i).getValue() == this.panJeu.getGt().getTuiles().get(i+1).getValue()){
					++cpt;
				}
			}
			else if (i == 3) continue;
			else if (i < 7){
				if (this.panJeu.getGt().getTuiles().get(i).getValue() == this.panJeu.getGt().getTuiles().get(i+1).getValue()){
					++cpt;
				}
			}
			else if (i == 7) continue;
			else if (i < 11){
				if (this.panJeu.getGt().getTuiles().get(i).getValue() == this.panJeu.getGt().getTuiles().get(i+1).getValue()){
					++cpt;
				}
			}
			else if (i == 11) continue;
			else if (i < 15){
				if (this.panJeu.getGt().getTuiles().get(i).getValue() == this.panJeu.getGt().getTuiles().get(i+1).getValue()){
					++cpt;
				}
			}
		}
		
		return cpt;
	}
	
	/**
	 * Cette méthode vérifie le nombre de fusions possibles sur les lignes verticales et renvoie le nombre de fusions possibles
	 * @return int
	 */
	public int hautBasIA(){
		int cpt = 0;
		
		for(int i = 0; i < 4; ++i){
			if (this.panJeu.getGt().getTuiles().get(i).getValue() == this.panJeu.getGt().getTuiles().get(i+4).getValue()){
				++cpt;
			}
			else if (this.panJeu.getGt().getTuiles().get(i).getValue() == this.panJeu.getGt().getTuiles().get(i+8).getValue()){
				++cpt;
			}
			else if (this.panJeu.getGt().getTuiles().get(i).getValue() == this.panJeu.getGt().getTuiles().get(i+12).getValue()){
				++cpt;
			}
		}
		
		return cpt;
	}
	
}

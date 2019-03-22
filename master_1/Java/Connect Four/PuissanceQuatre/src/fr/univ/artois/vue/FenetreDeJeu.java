package fr.univ.artois.vue;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import fr.univ.artois.controler.FenetreJeuControler;

/**
 * Vue principale
 * @author adam
 *
 */
public class FenetreDeJeu extends JFrame implements Observer{
	
	protected FenetreJeuControler controler;
	protected PanelPrincipal panJeu = new PanelPrincipal();
	protected PanelFinDePartie finPartie = new PanelFinDePartie();
	public static int tour = 0;
	
	//Identifiant pour le panel de fin de partie
	public final static String nomPanelFin = new String("nomPanelFin");
	
	public FenetreDeJeu(FenetreJeuControler c){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setPreferredSize(new Dimension(700,700));
		this.panJeu.add(this.finPartie, nomPanelFin);
		this.setContentPane(this.panJeu);
		this.pack();
		this.setTitle("Connect Four");
		this.controler = c;
		this.ajoutEcouteurs();
		this.setVisible(true);
	}
	
	/**
	 * Cette méthode permet d'ajouter les écouteurs aux cellules
	 */
	public void ajoutEcouteurs(){
		for(int i = 0; i < 42; ++i){
			this.panJeu.grille.getComponent(i).addMouseListener(new CelluleListener());
		}
	}
	
	@Override
	/**
	 * Cette méthode prend en compte le retour du modèle et modifie la vue en conséquence
	 */
	public void update(String str, int i, int j, int to) {
		// TODO Auto-generated method stub
		if (str.equals("checkedYellow")){
			
			//Retrouvaille de la cellule préssée (joli titre de film parodique)
			int cpt = 0;
			boolean flag = true;
			for(int a = 0; a < 6; ++a){
				for(int b = 0; b < 7 && flag; ++b, ++cpt){
					if (a == i && b == j){
						flag = false;
						((Cellule)this.panJeu.grille.getComponent(cpt)).setImage(new ImageIcon("Sprites/jaune.png"));
						break;
					}
				}
			}
			
		}
		
		else if (str.equals("checkedRed")){
			int cpt = 0;
			boolean flag = true;
			for(int a = 0; a < 6; ++a){
				for(int b = 0; b < 7 && flag; ++b, ++cpt){
					if (a == i && b == j){
						flag = false;
						((Cellule)this.panJeu.grille.getComponent(cpt)).setImage(new ImageIcon("Sprites/rouge.png"));
						break;
					}
				}
			}
		}
		
		else if (str.equals("fullChecked")){
			//Changement de panel vers celui de fin
			CardLayout cl = (CardLayout)this.panJeu.getLayout();
			cl.show(this.panJeu, nomPanelFin);
		}
		
		else{
			tour += 1;
			this.finPartie.getGagne().setText("Le joueur "+tour+" a gagné !");
			CardLayout cl = (CardLayout)this.panJeu.getLayout();
			cl.show(this.panJeu, nomPanelFin);
		}
		
		if (tour == 0) tour = 1;
		else tour = 0;
		
		repaint();
		
	}
	
	class CelluleListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			FenetreDeJeu.this.controler.control(((Cellule)e.getSource()).getId(), tour);
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
}

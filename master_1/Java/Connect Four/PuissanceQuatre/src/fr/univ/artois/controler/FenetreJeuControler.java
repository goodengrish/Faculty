package fr.univ.artois.controler;

import fr.univ.artois.modele.AbstractModele;
import fr.univ.artois.modele.PanJeuModele;

public class FenetreJeuControler extends AbstractControler{

	public FenetreJeuControler(PanJeuModele m) {
		this.fenetreJeu = m;
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Cette méthode utilisera les méthodes du modèle, puis modifira la vue à 
	 * travers la méthode notifyObserver. Enfin la méthode update de la vue sera
	 * appelée pour se modifier en conséquence du message envoyée par le modèle
	 */
	public void control(int id, int tour){
		//Appels des méthodes des analyseurs du modèle
		
		int cpy = tour;
		int col, lig;
		++cpy;
		
		int isDone;
		
		//Récupération des coordonées de la cellule actionnée
		if (id >= 0 && id <= 6){
			col = id;
			lig = 0;
		}
		else if (id >= 7 && id <= 13) {
			col = id%7; 
			lig = 1;
		}
		else if (id >= 14 && id <= 20){
			col = id%14; 
			lig = 2;
		}
		else if (id >= 21 && id <= 27){
			col = id%21; 
			lig = 3;
		}
		else if (id >= 28 && id <= 34){
			col = id%28; 
			lig = 4;
		}
		else {
			col = id%35;
			lig = 5;
		}
		
		int colOK = this.fenetreJeu.getAnalyseur().lignesOk(this.fenetreJeu.getGrille(), col);
		
		//Rappel: 1 pour OK, 0 sinon
		if (colOK == 1){
			for(int i = 5; i >= 0; --i){
				if (this.fenetreJeu.getGrille()[i][col] == 0){
					
					if (tour == 0){
						this.fenetreJeu.getGrille()[i][col] = 1;
						
						//Vérification des fins de partie
						isDone = this.fenetreJeu.getAnalyseur().ligneHorizontale(this.fenetreJeu.getGrille(), cpy);
						
						if (isDone == 1) this.fenetreJeu.notifyObserver("horizonChecked", 0, 0, tour);
						
						isDone = this.fenetreJeu.getAnalyseur().ligneVerticale(this.fenetreJeu.getGrille(), cpy);
						
						if (isDone == 1) this.fenetreJeu.notifyObserver("verticalChecked", 0, 0, tour);
						
						isDone = this.fenetreJeu.getAnalyseur().grillePleine(this.fenetreJeu.getGrille(), cpy);
						
						if (isDone == 1) if (isDone == 1) this.fenetreJeu.notifyObserver("fullChecked", 0, 0, tour);
						
						this.fenetreJeu.notifyObserver("checkedYellow", i, col, tour);
						
					}
					else{
						this.fenetreJeu.getGrille()[i][col] = 2;
						
						//Vérification des fins de partie 
						isDone = this.fenetreJeu.getAnalyseur().ligneHorizontale(this.fenetreJeu.getGrille(), cpy);
						
						if (isDone == 1) this.fenetreJeu.notifyObserver("horizonChecked", 0, 0, tour);
						
						isDone = this.fenetreJeu.getAnalyseur().ligneVerticale(this.fenetreJeu.getGrille(), cpy);
						
						if (isDone == 1) this.fenetreJeu.notifyObserver("verticalChecked", 0, 0, tour);
						
						isDone = this.fenetreJeu.getAnalyseur().grillePleine(this.fenetreJeu.getGrille(), cpy);
						
						if (isDone == 1) if (isDone == 1) this.fenetreJeu.notifyObserver("fullChecked", 0, 0, tour);
						
						this.fenetreJeu.notifyObserver("checkedRed", i, col, tour);
						
					}
					break;
				}
			}
		}
	}
	
}

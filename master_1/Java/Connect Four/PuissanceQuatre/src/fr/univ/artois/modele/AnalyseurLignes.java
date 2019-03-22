package fr.univ.artois.modele;


/**
 * Cette classe va analyser les lignes de la grille pour vérifier si ces dernières sont aptes à accueillir
 * une nouvelle cellule
 * @author adam
 *
 */
public class AnalyseurLignes {
	
	/**
	 * Vérifie si une ligne verticale peut changer d'étât (changer de type de cellule)
	 * @param grille
	 * @param col
	 * @return int
	 */
	public static int lignesOk(int[][] grille, int col){
		for(int i = 5; i >= 0; --i){
			if (grille[i][col] == 0){
				return 1;
			}
		}
		return 0;
	}
	
	/**
	 * Cette méthode permet de vérifier si un joueur a une ligne horizontale pleine (ce joueur est alors gagnant)
	 * rappel: 0 vide 1 jaune 2 rouge
	 * @param grille
	 * @param couleur
	 * @return int: 0 pour faux et 1 pour vrai
	 */
	public static int ligneHorizontale(int[][] grille, int couleur){
		int tmp;
		for(int i = 0; i < 6; ++i){
			tmp = 0;
			for(int j = 0; j < 7; ++j){
				if (tmp == 4) return 1;
				if (grille[i][j] == couleur){
					tmp++;
					if (tmp == 4) return 1;
				}
				else tmp = 0;
			}
		}
		return 0;
	}
	
	/**
	 * Cette méthode vérifie qu'un joueur a rempli une ligne verticale (et est ainsi victorieux)
	 * @param grille
	 * @param couleur
	 * @return int: 0 pour faux et 1 pour vrai
	 */
	public static int ligneVerticale(int[][] grille, int couleur){
		int tmp;
		for(int j = 0; j < 7; ++j){
			tmp = 0;
			for(int i = 5; i >= 0; --i){
				if (tmp == 4) return 1;
				if (grille[i][j] == couleur){
					tmp++;
					if (tmp == 4) return 1;
				}
				else tmp = 0;
			}
		}
		return 0;
	}
	
	/**
	 * Méthode non implémentée
	 * @param grille
	 * @param couleur
	 * @return int: 0 pour faux et 1 pour vrai
	 */
	public static int ligneDiagonale(int[][] grille, int couleur){
		return 0;
	}
	
	/**
	 * 
	 * @param grille
	 * @param couleur
	 * @return int: 0 pour faux et 1 pour vrai
	 */
	public static int grillePleine(int[][] grille, int couleur){
		int cpt = 0;
		for(int i = 0; i < 6; ++i){
			for(int j = 0; j < 7; ++j){
				if (grille[i][j] != 0) cpt++;
			}
		}
		return (cpt == 6*7 ? 1 : 0);
	}
	
}

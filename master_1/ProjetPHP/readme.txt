Mettre un menu déroulant pour l'état civil

Réaliser les connexions à la base de données lors des inscriptions et des connexions

Sécuriser les inscriptions contre les requêtes SQL

Lors d'une inscription, donner 1000 euros à chaque nouveau utilisateur
	-> ajouter un attribut dans la base: int wallet

à faire:
3) enregistrer les actions favorites de chaque utilisateur dans la base

4) proposer des options d'affichage pour les graphiques

5) modifier la valeur du cookie userValid par l'id de l'utilisateur (pour
permettre par la suite d'enregistrer les actions/enchères dans la base)

problèmes:
nouvelles inscriptions impossible ligne 22 fichier menuinscription.php

idée d'amélioration

1) utiliser ajax/json (je ne sais plus lequel est ce) pour les changements de panel dans le
menu utilisateur. Pour éviter que la page se rafraîchisse complétement, mais seulement le panel en question

-- VERY IMPORTANT
Penser à tout passer au validateur
La base de données doit être sécurisée. Par de récupération de la base ni d'injections de code

changer les droits d'utilisation sur la base de données

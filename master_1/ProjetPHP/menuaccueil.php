<?php
	session_start();
	
	if (isset($_COOKIE['userValid'])){
		header("Location: menuutilisateur.php");
		//exit;
	}

	if (isset($_POST['connexion'])){
		header("Location: menuconnexion.php");
		exit;
	}

	if (isset($_POST['inscription'])){
		header("Location: menuinscription.php");
		exit;
	}

?>
<!DOCTYPE html>
<html lang="fr">

	<head>
		<meta charset="utf-8"/>
		<!-- On vérifie si l'utilisateur utilise et possède la dernière version d'IE (s'il utilise IE bien sûr) -->
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<!-- Ligne pour les mobiles: on demande à ce que la page couvre l'intégralité de l'écran (intial-scale 1 signifie sans zoom) -->
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<!-- Récupération du css et js de Bootstrap du dossier-->
		<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
		<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
		<!-- Récupération des ressources externes pour certaines fonctionnalités de Bootstrap (chargement plus vif) -->
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
		<!-- Récupération de la feuille de style du dossier -->
		<link rel="stylesheet" type="text/css" href="style.css" >
		<!-- Récupération et placement de l'icône de la page -->
		<link rel="icon" type="image/ico" href="icons/Graphicloads-Flat-Finance-Flow-market.ico">
		<title>Artois Analytics</title>
	</head>

	<body>
		<!-- container: conteneur avec une largeur responsive fixée -->
		<!-- container-fluid: conteneur prenant la largeur max du support -->
		<div class="container-fluid">
			<header class="row">
				<h1 align="center" id="titre">Artois Analytics</h1>
				<div class="col-xs-offset-1 col-xs-1 col-sm-offset-2 col-sm-2 col-md-offset-2 col-md-2 col-lg-offset-9 col-lg-2">
				<form method="POST" action="menuaccueil.php">
					<button type="submit" class="btn btn-primary" name="connexion">Se connecter <span class="glyphicon glyphicon-log-in"></span>
				</form>
				</div>
				<div class="col-xs-offset-1 col-xs-1 col-sm-offset-2 col-sm-2 col-md-offset-2 col-md-2 col-lg-offset-9 col-lg-2">
				<form method="POST" action="menuaccueil.php">
					<button type="submit" class="btn btn-primary" name="inscription" >Créer un compte <span class="glyphicon glyphicon-check">
				</form>
				</span>
				</div>
			</header>
			<hr>
			<section class="row">
				<div id="one" class="jumbotron">
					<p>Cette application de bourse en ligne permet de prendre en compte une multitude d'utilisateurs ainsi que leurs coordonnées</p>
					<img id="net" src="icons/network.png" alt="network" class="img-circle">
				</div>
				<div id="two" class="jumbotron">
					<p>Elle permet une gestion efficace du porte-monnaie virtuel (achats/ventes, graphiques)</p>
					<img id="wallet" src="icons/money-bag.png" alt="network" class="img-circle">
				</div>
				<div id="three" class="jumbotron">
					<p>Tableau de bord personnalisable</p>
					<img id="tabloid" src="icons/calculator.png" alt="network" class="img-circle">
				</div>
				<div id="four" class="jumbotron">
					<p>Gestion des alertes</p>
					<img id="warning" src="icons/warning.png" alt="network" class="img-circle">
				</div>
			</section>

			<footer>
				<p align="center">&copy; 2017 Antoine's and Adam's Project</p>
				<div align="center">Icons made by <a href="http://www.flaticon.com/authors/vectors-market" title="Vectors Market">Vectors Market</a> from <a href="http://www.flaticon.com" title="Flaticon">www.flaticon.com</a> is licensed by <a href="http://creativecommons.org/licenses/by/3.0/" title="Creative Commons BY 3.0" target="_blank">CC 3.0 BY</a></div>
			</footer>
		</div>

	</body>

</html>

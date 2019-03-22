<?php

	/*
	*Pour le bouton "Déconnexion", réinitialiser les cookies de session de l'utilisateur
	* Une fois supprimés, on revient au menu d'accueil
	**/

	session_start();

	if($_SESSION['id']=="" && $_SESSION['pseudo']==""){
	   	session_unset();
		header('Location: menuaccueil.php');
	}

	$apiRequests = "http://download.finance.yahoo.com/d/quotes.csv?s=";

	if (isset($_POST['deconnexion'])){
		header("Location: menudeconnexion.php");
		exit;
	}

	//Récupération des choix des actions de l'utilisateur
	if (isset($_POST['actionsChoices'])){
		$str = $apiRequests;
		if (isset($_POST['google'])){
			$str .= 'GOOG';
			if(isset($_POST['apple']) or isset($_POST['microsoft'])) {
				$str .= '+';
			}
		}
		if (isset($_POST['apple'])){
			$str .= 'AAPL';
			if (isset($_POST['microsoft'])){
				$str .= '+';
			}
		}
		if (isset($_POST['microsoft'])){
			$str .= 'MSFT';
		}

		$str .= '&f=nab';

		$curlSession = curl_init($str);
		curl_setopt($curlSession, CURLOPT_RETURNTRANSFER, TRUE);

		$res = curl_exec($curlSession);

		echo $res;
		/*echo gettype($res);*/

		/*
		Je récupére les choix d'actions de l'utilisateur et les sauvegarde
		dans la base de données
		*/

		curl_close($curlSession);

		unset($_POST['google']);
		unset($_POST['apple']);
		unset($_POST['microsoft']);

	}

	//On switche le contenu du div zoneContenu
	function selectionContenu(){

		$pdo = new PDO('sqlite:'.dirname(__FILE__).'/.htbase.db');

		if (!isset($_POST['portefeuille']) && !isset($_POST['achatsventes']) && !isset($_POST['graphiques']) && !isset($_POST['alertes'])){
			$str = "<p> Veuillez utiliser la barre de gauche pour afficher le contenu désiré </p><br>";
			return $str;
		}

		/*
		Evolution du solde du porte feuille
		*/
		if (isset($_POST['portefeuille'])){
			//Requête pour récupérer les sommes du porte monnaie de l'utilisateur
			try{
				$pdo = new PDO('sqlite:'.dirname(__FILE__).'/.htbase.db');
				$rq = "select somme from PorteMonnaie where id_user = :id order by date desc";
				$request = $pdo->prepare($rq);
				$request->execute(array(':id' => $_SESSION['id']));
				$sommes = $request->fetchAll();
				
				$tableauSomme = array();
				$i = 0;
				for(; $i < 10; $i++){
					if ($i < sizeof($sommes)){
						$tableauSomme[] = $sommes[$i]['somme'];
					}
					else{
							$tableauSomme[] = 0;
					}
				}
				
				
			}
			catch (PDOException $e){
				$e->getMessage();
			}
			
			//echo "taille des sommes: ".sizeof($tableauSomme);
			
			for(; $i < 10; ++$i){
					$tableauSomme[] = 0;
			}

			//Requête pour récupérer les dates du porte monnaie
			try{
				$pdo = new PDO('sqlite:'.dirname(__FILE__).'/.htbase.db');
				$rq = "select date from PorteMonnaie where id_user = :id order by date desc";
				$request = $pdo->prepare($rq);
				$request->execute(array(':id' => $_SESSION['id']));
				$dates = $request->fetchAll();
				
				$tableauDate = array();
				$i = 0;
				
				for(; $i < 10; $i++){
					if ($i < sizeof($dates)){
						$tableauDate[] = $dates[$i]['date'];
					}
					else{
						$tableauDate[] = 0;
					}
				}
				
			}
			catch (PDOException $e){
				$e->getMessage();
			}

			$str = "<canvas id='myChart' width='400' height='400'></canvas>
			<script>
			var ctx = document.getElementById('myChart');
			var myChart = new Chart(ctx, {
			    type: 'bar',
			    data: {
				labels: ['".$tableauDate[0]."','".$tableauDate[1]."','".$tableauDate[2]."', '".$tableauDate[3]."', '".$tableauDate[4]."', '".$tableauDate[5]."','".$tableauDate[6]."','".$tableauDate[7]."','".$tableauDate[8]."','".$tableauDate[9]."'],
				datasets: [{
				    label: 'Valorisation du porte-monnaie au fil du temps',
				    data: [".$tableauSomme[0].",".$tableauSomme[1].",".$tableauSomme[2].",".$tableauSomme[3].",".$tableauSomme[4].",".$tableauSomme[5].",".$tableauSomme[6].",".$tableauSomme[7].",".$tableauSomme[8].",".$tableauSomme[9]."],
				    backgroundColor: [
					'rgba(255, 99, 132, 0.2)',
					'rgba(54, 162, 235, 0.2)',
					'rgba(255, 206, 86, 0.2)',
					'rgba(75, 192, 192, 0.2)',
					'rgba(153, 102, 255, 0.2)',
					'rgba(255, 159, 64, 0.2)',
					'rgba(255, 99, 132, 0.2)',
					'rgba(54, 162, 235, 0.2)',
					'rgba(255, 206, 86, 0.2)',
					'rgba(75, 192, 192, 0.2)',
				    ],
				    borderColor: [
					'rgba(255,99,132,1)',
					'rgba(54, 162, 235, 1)',
					'rgba(255, 206, 86, 1)',
					'rgba(75, 192, 192, 1)',
					'rgba(153, 102, 255, 1)',
					'rgba(255, 159, 64, 1)',
					'rgba(255,99,132,1)',
					'rgba(54, 162, 235, 1)',
					'rgba(255, 206, 86, 1)',
					'rgba(75, 192, 192, 1)',
				    ],
				    borderWidth: 1
				}]
			    },
			    options: {
				scales: {
				    yAxes: [{
					ticks: {
					    beginAtZero:true
					}
				    }]
				}
			    }
			});
			</script>";
			return $str;
		}


		else if (isset($_POST['achatsventes'])){
			$str = "<p> Achat/Vente </p>";

			try{
	          $idU = $_SESSION['id'];
	          $rq = 'select * from Action where id_user = :idU';

	          $request = $pdo->prepare($rq);
	          $request->execute(array(':idU' => $idU));
	        }
	        catch (PDOException $e){
	          echo $e->getMessage();
	        }

	        $tmp = $request->fetch();

			/*
			Si l'utilisateur n'a pas d'actions, on lui propose une liste des entreprises
			existantes avec l'api et une fois valider, on enregistre le tout dans la base de données
			Sinon, on affiche les graphes correspondants à ses actions favorites
			*/
			if ($tmp == ""){
				$str .= "<form method='post' action='ajoutAction.php'>
						<p>Choisissez vos actions boursières:</p>
				";

				try{
		          $idU = $_SESSION['id'];

		          $rq = 'select * from Entreprise';
		          $request = $pdo->prepare($rq);
		          $request->execute();
		        }
		        catch (PDOException $e){
		          echo $e->getMessage();
		        }

		        $tmp = $request->fetchall();

		        foreach($tmp as $i){
		        	$tmp2 = ''.$i['nom'].'';
		        	$str .="<input type='checkbox' name ='".$tmp2."' value='".$tmp2."'>".$i['nom']."<br>
		        		";
		        }

		        $str .= "<button type='submit' class='btn btn-primary' name='submitGraph'>Valider</button>
		        	";

				$str .= "</form>
					";

				return $str;
			}
			else{
				$cpt=0;
				try{
		          $idU = $_SESSION['id'];

		          $rq = 'select e.id as id, e.nom as nom, e.slogan as slogan from Action a inner join Entreprise e on a.id_entreprise = e.id where a.id_user = :idU';
		          $request = $pdo->prepare($rq);
		          $request->execute(array('idU' => $idU));
		        }
		        catch (PDOException $e){
		          echo $e->getMessage();
		        }

		        $tmp = $request->fetchall();

		        $str .= "<label for='entreprise'>Choix de l'entreprise</label>
		        		<form name='entreprise' method='post' action='transactionAction.php'>
						<select name='selection'>
					";

				foreach($tmp as $i){
					if($cpt==0){
						$str .= "<option value='".$i['id']."' selected>".$i['nom']."<br>
							";
					}else{
						$str .= "<option value='".$i['id']."'>".$i['nom']."<br>
							";
					}
					$cpt++;
				}

				$str .= "</select><br>
					";

				$str .= "<button type='submit' class='btn btn-primary' name='action'>Selectionner</button><br>
		        	";

		        /*<select name="selection">
					<option selected>
					<option>
				</select>*/
			}

			return $str;
		}

		/*
		Dans un premier temps, récupérer les actions favorites de l'utilisateur connecté
		Si aucunes actions favorites détectées, proposer les actions boursières existantes
		dans l'api Yahoo
		*/
		else if (isset($_POST['graphiques'])){

			//On récupére de la base de données les actions favorites de l'utilisateur

			$str = "<p> Graphiques </p>";
			return $str;
		}


		else if (isset($_POST['alertes'])){
			$str = "<p> Alertes </p>";
			
			$rq = "select * from Enchere where id = :id";
			$request = $pdo->prepare($rq);
			$request->execute(array(':id' => $_SESSION['id']));
			
			$tmp = $request->fetchall();

			foreach($tmp as $i){
				$rq = "select * from Entreprise where id = :id";
				$request = $pdo->prepare($rq);
				$request->execute(array(':id' => $i['id_entreprise']));
				
				$tmp2 = $request->fetch();
				
				$apiRequests = "http://download.finance.yahoo.com/d/quotes.csv?s=".$tmp2['slogan']."&f=nab";

				$curlSession = curl_init($apiRequests);
				
				$option = array(CURLOPT_RETURNTRANSFER => TRUE,
								CURLOPT_PROXY => "cache-etu.univ-artois.fr:3128"
							);
				//curl_setopt($curlSession, CURLOPT_RETURNTRANSFER, TRUE);
				//curl_setopt($curlSession,CURLOPT_PROXY,"cache-etu.univ-artois.fr:3128");
				curl_setopt_array($curlSession, $option);

				$resCurl = curl_exec($curlSession);
				
				//echo $resCurl;

				$res2 = explode(",",$resCurl);

				$prixActionCourante = $res2[1];
				
				if(($i['prixAchat']-$prixActionCourante)<20){
					$str .= "
					  <div class='alert alert-warning'>
					  <strong> Attention: </strong>Le prix courant pour une action de l'entreprise ".$tmp2['nom']." a beaucoup baissé par rapport au prix d'achat.
					  </div>
					  ";
				}
				
				curl_close($curlSession);
			}
			
			return $str;
		}
	}
	
	function bienvenue(){
		try{
			$pdo = new PDO('sqlite:'.dirname(__FILE__).'/.htbase.db');
			$rq = "select pseudo from Utilisateur where id = :id";
			$request = $pdo->prepare($rq);
			$request->execute(array(':id' => $_SESSION['id']));
			$tmp = $request->fetch();
			echo "Bienvenu ".$tmp['pseudo']."<br>";
		}
		catch (PDException $e){
			echo $e->getMessage();
		}

		try{
			$pdo = new PDO('sqlite:'.dirname(__FILE__).'/.htbase.db');
			$rq = "select sommeCourante from Utilisateur where id = :id";
			$request = $pdo->prepare($rq);
			$request->execute(array(':id' => $_SESSION['id']));
			$tmp = $request->fetch();
			echo "Solde actuel: ".$tmp['sommeCourante']."$";
		}
		catch (PDException $e){
			echo $e->getMessage();
		}

	}

	selectionContenu();

?>
<!DOCTYPE html>
<html lang="fr">

	<head>

		<meta charset="utf-8"/>
		<link rel="icon" type="image/ico" href="icons/Graphicloads-Flat-Finance-Flow-market.ico">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>Artois Analytics</title>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
		<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
		<link rel="stylesheet" type="text/css" href="style.css">
		<script src="Chart.js"></script>

	</head>
	<body>
		
		<section id="bienvenu">
			<?php echo bienvenue(); ?>
		</section>
		
		<div class="container-fluid">

			<header class="row">

				<h1 align="center" id="titre">Artois Analytics</h1>
					<div class="col-xs-offset-1 col-xs-1 col-sm-offset-2 col-sm-2 col-md-offset-2 col-md-2 col-lg-offset-9 col-lg-2">
						<form method="POST" action="menudeconnexion.php">
							<button type="submit" class="btn btn-primary" name="deconnexion">Déconnexion<span class="glyphicon glyphicon-off"></span></button>
						</form>
					</div>

			</header>

			<hr>

			<div class="row">
				<section id="menuOptions" class="col-lg-6">
					<form method="POST" action="menuutilisateur.php">
						<button type="submit" class="btn btn-primary" name="portefeuille">Porte-Feuille</button>
						<button type="submit" class="btn btn-primary" name="achatsventes">Achats-Ventes</button>
						<button type="submit" class="btn btn-primary" name="graphiques">Graphiques</button>
						<button type="submit" class="btn btn-primary" name="alertes">Alertes</button>
					</form>
				</section>
				<section id="zoneContenu" class="col-lg-6">
				<?php
					echo selectionContenu();
				?>
				</section>
			</div>

		</div>

	</body>

</html>

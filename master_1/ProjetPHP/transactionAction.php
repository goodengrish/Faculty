<?php
	session_start();

	function nomEntreprise(){
		$pdo = new PDO('sqlite:'.dirname(__FILE__).'/.htbase.db');

		if(isset($_POST['action'])){

			try{
	          $rq = 'select * from Entreprise where id = :idE';
	          $request = $pdo->prepare($rq);
	          $request->execute(array(':idE' => $_POST['selection']));
	        }
	        catch (PDOException $e){
	          echo $e->getMessage();
	        }

	        $tmp = $request->fetch();

	        echo $tmp['nom'];
		}
	}

	function transaction(){
		$pdo = new PDO('sqlite:'.dirname(__FILE__).'/.htbase.db');

		if(isset($_POST['action'])){

			try{
	          $rq = 'select * from Entreprise where id = :idE';
	          $request = $pdo->prepare($rq);
	          $request->execute(array(':idE' => $_POST['selection']));
	        }
	        catch (PDOException $e){
	          echo $e->getMessage();
	        }

	        $tmp = $request->fetch();

	        $res = $tmp['slogan'];

	        $apiRequests = "http://download.finance.yahoo.com/d/quotes.csv?s=".$res."&f=nab";

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

			$str =$prixActionCourante;

			curl_close($curlSession);	
		}

		return $str;
	}

	

	function valider(){
		$pdo = new PDO('sqlite:'.dirname(__FILE__).'/.htbase.db');
		if(isset($_POST['submitAchatAct'])){
			if(isset($_POST['nombre'])){
				if($_POST['nombre'] > 0){
					try{
			          $rq = 'select * from Utilisateur where id = :idU';
			          $request = $pdo->prepare($rq);
			          $request->execute(array(':idU' => $_SESSION['id']));
			        }
			        catch (PDOException $e){
			          echo $e->getMessage();
			        }

			        $tmp = $request->fetch();

			        if($_POST['nombre']*$_POST['prix']>$tmp['sommeCourante']){
						$_SESSION['erreurPasAssezArgent']=1;
			        }else{
			        	date_default_timezone_set('America/New_York');
		            	$date = date("Y-m-d");

		            	try{
				          $rq = 'select * from Entreprise where id = :idE';
				          $request = $pdo->prepare($rq);
				          $request->execute(array(':idE' => $_POST['choix']));
				        }
				        catch (PDOException $e){
				          echo $e->getMessage();
				        }

				        $tmp = $request->fetch();

				        $res = $tmp['id'];

		            	try{
				          $rq = 'INSERT INTO Enchere(date,nbrActions,prixAchat,id_user,id_entreprise) values (:date,:nbrA,:prix,:idU,:idE)';
				          $request = $pdo->prepare($rq);
				          $request->execute(array(':date' => $date,':nbrA' => $_POST['nombre'],'prix' => $_POST['prix'],'idU' => $_SESSION['id'],'idE' => $res));
				        }
				        catch (PDOException $e){
				          echo $e->getMessage();
				        }

				        try{
				          $rq = 'select * from Utilisateur where id = :idU';
				          $request = $pdo->prepare($rq);
				          $request->execute(array(':idU' => $_SESSION['id']));
				        }
				        catch (PDOException $e){
				          echo $e->getMessage();
				        }

				        $tmp = $request->fetch();

				        $res = $tmp['sommeCourante'];
				        
				        $sommeF = $res - ($_POST['nombre']*$_POST['prix']);
				        
				        try{
				          $rq = "INSERT INTO PorteMonnaie(somme,date,id_user) values (:somme,:date,:idUser);";

				          $request = $pdo->prepare($rq);
				          $request->execute(array(':somme' => $sommeF, ':date' => $date, ':idUser' => $_SESSION['id']));

				        }catch (PDOException $e){
				          echo $e->getMessage();
				        }

				        try{
				          $rq = "UPDATE Utilisateur SET sommeCourante = :sommeF WHERE id = :idU;";

				          $request = $pdo->prepare($rq);
				          $request->execute(array(':sommeF' => $sommeF, ':idU' => $_SESSION['id']));

				          header("Location: menuutilisateur.php");
				        }catch (PDOException $e){
				          echo $e->getMessage();
				        }
				    }
		        }else{
					$_SESSION['erreurAchatNbrInvalide']=1;
		        }

		    }
		}else if(isset($_POST['submitVendAct'])){
			try{
	          $rq = 'select * from Enchere where id_user = :idU and id_entreprise = :idE';
	          $request = $pdo->prepare($rq);
	          $request->execute(array(':idU' => $_SESSION['id'], ':idE' => $_POST['choix']));
	        }
	        catch (PDOException $e){
	          echo $e->getMessage();
	        }

	        $tmp = $request->fetchall();

	        if($tmp!=""){
	        	$sommeT = 0;
	        	$nombreActionTotal = 0;

	        	foreach($tmp as $i){
	        		$sommeT += $i['nbrActions'] * $i['prixAchat'];
	        		$nombreActionTotal += $i['nbrActions'];
	        	}
	        	
	        	if($_POST['nombre']==0){
					$_SESSION['erreurPasNombreEntre']=1;
				}else if($nombreActionTotal < $_POST['nombre']){
	        		$_SESSION['erreurTropActionAVendre']=1;
	        	}else{
	        		try{
			          $rq = "DELETE FROM Enchere WHERE id_user = :idU and id_entreprise = :idE;";

			          $request = $pdo->prepare($rq);
			          $request->execute(array(':idU' => $_SESSION['id'], 'idE' => $_POST['choix']));

			        }catch (PDOException $e){
			          echo $e->getMessage();
			        }

			        try{
			          $rq = 'SELECT * FROM Utilisateur where id = :idU';
			          $request = $pdo->prepare($rq);
			          $request->execute(array(':idU' => $_SESSION['id']));
			        }
			        catch (PDOException $e){
			          echo $e->getMessage();
			        }

			        $tmp = $request->fetch();

			        $sommeF = $tmp['sommeCourante'] + $sommeT;

			        date_default_timezone_set('America/New_York');
	            	$date = date("Y-m-d");
			        
			        try{
			          $rq = "INSERT INTO PorteMonnaie(somme,date,id_user) values (:somme,:date,:idUser);";

			          $request = $pdo->prepare($rq);
			          $request->execute(array(':somme' => $sommeF, ':date' => $date, ':idUser' => $_SESSION['id']));

			        }catch (PDOException $e){
			          echo $e->getMessage();
			        }

			        try{
			          $rq = "UPDATE Utilisateur SET sommeCourante = :sommeF WHERE id = :idU;";

			          $request = $pdo->prepare($rq);
			          $request->execute(array(':sommeF' => $sommeF, ':idU' => $_SESSION['id']));

			          header("Location: menuutilisateur.php");
			        }catch (PDOException $e){
			          echo $e->getMessage();
			        }

			        header("Location: menuutilisateur.php");
	        	}

	        }	
		}else if(isset($_POST['retour'])){
			header("Location: menuutilisateur.php");
		}
	}
	
	function afficheErreur(){
		if($_SESSION['erreurAchatNbrInvalide']==1){
			$_SESSION['erreurAchatNbrInvalide']=0;
			return "
			  <div class='alert alert-warning'>
			  <strong> Achat impossible: </strong>Nombre d'action invalide.
			  </div>";
		}else if($_SESSION['erreurPasAssezArgent']==1){
			$_SESSION['erreurPasAssezArgent']=0;
			return "
			  <div class='alert alert-warning'>
			  <strong> Achat impossible: </strong>Pas assez d'argent.
			  </div>";
		}else if($_SESSION['erreurTropActionAVendre']==1){
			$_SESSION['erreurTropActionAVendre']=0;
			return "
			  <div class='alert alert-warning'>
			  <strong> Vente impossible: </strong>Vous avez pas autant d'action à vendre.
			  </div>";
		}else if($_SESSION['erreurPasNombreEntre']==1){
			$_SESSION['erreurPasNombreEntre']=0;
			return "
			  <div class='alert alert-warning'>
			  <strong> Vente impossible: </strong>Vous avez pas mis un nombre d'action à vendre valide.
			  </div>";
		}else{
			
		}
	}

	valider();
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
		
		<div class="container-fluid">

			<header class="row">

				<h1 align="center" id="titre">Artois Analytics</h1>
					<div class="col-xs-offset-1 col-xs-1 col-sm-offset-2 col-sm-2 col-md-offset-2 col-md-2 col-lg-offset-9 col-lg-2">
						<form method="POST" action="menudeconnexion.php">
							<button type="submit" class="btn btn-primary" name="deconnexion">Déconnexion<span class="glyphicon glyphicon-off"></span></button>
						</form>

			</header>

			<hr>

			<div class="row">
				<div class="col-lg-3">
				</div>
				<section id="menuOptions" class="col-lg-6">
				<form name='action' method='post' action='transactionAction.php'>
					<?php if(isset($_POST['nomE'])){ echo $_POST['nomE']; }else{ echo nomEntreprise(); }  ?>
					<input type='number' name='nombre' value='nombre'>
					<p>Prix de l'action courante : <?php if(isset($_POST['prix'])){ echo $_POST['prix']; }else{ echo transaction(); } ?>$</p>
					<input type="hidden" name="prix" value=<?php if(isset($_POST['prix'])){ echo $_POST['prix']; }else{ echo transaction(); } ?>>
					<input type="hidden" name="nomE" value=<?php if(isset($_POST['nomE'])){ echo $_POST['nomE']; }else{ echo nomEntreprise(); } ?>>
					<input type="hidden" name="choix" value=<?php if(isset($_POST['choix'])){ echo $_POST['choix']; }else{ echo $_POST['selection']; } ?>>
					<button type='submit' class='btn btn-primary' name='submitAchatAct'>Acheter</button>
					<button type='submit' class='btn btn-primary' name='submitVendAct'>Vendre</button>
					<button type='submit' class='btn btn-primary' name='retour'>Retour</button>
					<br><br><?php echo afficheErreur(); ?>
				</form>
				</section>
				<div class="col-lg-3">
				</div>
			</div>

		</div>

	</body>

</html>

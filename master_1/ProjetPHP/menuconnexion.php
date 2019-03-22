<?php
  session_start();

  if (isset($_POST['retour'])){
    header("Location: menuaccueil.php");
    exit;
  }

  function connexion(){
    if (isset($_POST['testeConnexion'])){
      if ($_POST['pseudoOld'] != "" && $_POST['mdpOld'] != ""){
        //On vérifie que l'utilisateur existe dans la base
        try{

          $tmpMdp = $_POST['mdpOld'];
          $tmpMdp = sha1($tmpMdp);
          $tmpPseudo = $_POST['pseudoOld'];

          $rq = 'select * from Utilisateur where pseudo = :tmpPseudo and mdp = :tmpMdp';

          $pdo = new PDO('sqlite:'.dirname(__FILE__).'/.htbase.db');
          $request = $pdo->prepare($rq);
          $request->execute(array(':tmpPseudo' => $tmpPseudo, ':tmpMdp' => $tmpMdp));
        }
        catch (PDOException $e){
          echo $e->getMessage();
        }

        $tmp = $request->fetch();

        $_SESSION['id'] = $tmp['id'];
        $_SESSION['pseudo'] = $tmp['pseudo'];
		$_SESSION['erreurPasAssezArgent']=0;
		$_SESSION['erreurAchatNbrInvalide']=0;
		$_SESSION['erreurTropActionAVendre']=0;
		$_SESSION['erreurPasNombreEntre']=0;

        //L'utilisateur existe
        if ($tmp != ""){
          //setcookie('userValid', 'userValid', time()+3600*30);
          header("Location: menuutilisateur.php");
          exit;
        }
        //L'utilisateur n'existe pas
        else{
          return "
          <div class='alert alert-warning'>
          <strong> Connexion impossible: </strong>Utilisateur inconnu.
          </div>";
        }

      }
      else {
        return "
        <div class='alert alert-warning'>
        <strong> Connexion impossible: </strong>Champs manquants.
        </div>";
      }
    }
  }

  connexion();

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
    <header class="row">
      <h1 align="center" id="titre">Artois Analytics</h1>
      <div class="col-xs-offset-1 col-xs-1 col-sm-offset-2 col-sm-2 col-md-offset-2 col-md-2 col-lg-offset-9 col-lg-2">
      <form method="POST" action="menuconnexion.php">
        <button type="submit" class="btn btn-primary" name="retour" >Retour<span class="glyphicon glyphicon-chevron-left">
      </form>
    </header>
    <hr>

    <div class="jumbotron" id="jumbo">
      <form method="POST" action="menuconnexion.php">
        <p>Connexion</p><br>
        <label>Entrez votre pseudo: </label><input type="text" name="pseudoOld"><br>
        <label>Entrez votre mot de passe: </label><input type="password" name="mdpOld">
        <button type="submit" class="btn btn-primary" name="testeConnexion">Valider</button>
        <p> <?php echo connexion() ?> </p>
      </form>
    </div>

  </body>

</html>

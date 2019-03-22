<?php
  ob_start();
  session_start();

  if (isset($_POST['retour'])){
    header("Location: menuaccueil.php");
    exit;
  }

  function inscription(){
    if (isset($_POST['inscription'])){
      if ($_POST['pseudoNew'] != "" && $_POST['mdpNew'] != "" && isset($_POST['gender']) && $_POST['cardNew'] != ""){
        //On regarde dans la base si l'utilisateur existe déjà
        try{

          $pdo = new PDO('sqlite:'.dirname(__FILE__).'/.htbase.db');

          $tmpMdp = $_POST['mdpNew'];
          $tmpMdp = sha1($tmpMdp);
          $tmpPseudo = $_POST['pseudoNew'];
          $tmpCivil = $_POST['gender'];
          $tmpCard = $_POST['cardNew'];
          $present = false;

          if($tmpCivil=="homme"){
            $tmpCivil=0;
          }else{
            $tmpCivil=1;
          }

          try{
            $rq = "select * from Utilisateur where pseudo = :tmpPseudo";

            $request = $pdo->prepare($rq);
            $request->execute(array(':tmpPseudo' => $tmpPseudo));
          }
          catch (PDOException $e){
            echo $e->getMessage();
          }

          $tmp = $request->fetch();

          if($tmp != ""){
            $present = true;
            return "
            <div class='alert alert-warning'>
            <strong> Inscription impossible: </strong>Pseudo déjà utilisé.
            </div>";
            exit;
          }

          try{
            $rq = "select * from Utilisateur where :tmpCard = carteBanc";

            $request = $pdo->prepare($rq);
            $request->execute(array(':tmpCard' => $tmpCard));
          }
          catch (PDOException $e){
            echo $e->getMessage();
          }

          $tmp2 = $request->fetch();

          if($tmp2 != ""){
            $present = true;
            return "
            <div class='alert alert-warning'>
            <strong> Inscription impossible: </strong>Carte bancaire déjà utilisée pour un autre compte.
            </div>";
            exit;
          }

          //L'utilisateur existe déjà
          if ($present == false){
            try{
              $rq = "INSERT INTO Utilisateur(pseudo,mdp,etatCivil,carteBanc,sommeCourante) values (:tmpPseudo, :tmpMdp, :tmpCivil, :tmpCard, :tmpSomme);";

              $request = $pdo->prepare($rq);
              $request->execute(array(':tmpPseudo' => $tmpPseudo, ':tmpMdp' => $tmpMdp, ':tmpCivil' => $tmpCivil, ':tmpCard' => $tmpCard, ':tmpSomme' => 1000));

            }
            catch (PDOException $e){
              echo $e->getMessage();
            }

            try{
              $rq = "SELECT id from Utilisateur where pseudo = :pseudo;";

              $request = $pdo->prepare($rq);
              $request->execute(array(':pseudo' => $tmpPseudo));

              $tmp = $request->fetch();
              $idUtilisateur = $tmp['id'];
            }
            catch (PDOException $e){
              echo $e->getMessage();
            }

            date_default_timezone_set('America/New_York');
            $date = date("Y-m-d");

            try{
              $rq = "SELECT * from PorteMonnaie where id_user = :idU;";

              $request = $pdo->prepare($rq);
              $request->execute(array(':idU' => $idUtilisateur));

              $tmp = $request->fetch();
              if($tmp==""){
                $vide=true;
              }else{
                $vide=false;
              }
            }
            catch (PDOException $e){
              echo $e->getMessage();
            }

            if($vide==true){
              try{
                $rq = "INSERT INTO PorteMonnaie(somme,date,id_user) values (1000,:date,:idUser);";

                $request = $pdo->prepare($rq);
                $request->execute(array(':idUser' => $idUtilisateur, ":date" => $date));

                $tmp = $request->fetch();

                $_SESSION['id'] = $idUtilisateur;
                $_SESSION['pseudo'] = $tmpPseudo;
                $_SESSION['erreurPasAssezArgent']=0;
                $_SESSION['erreurAchatNbrInvalide']=0;
                $_SESSION['erreurTropActionAVendre']=0;
                $_SESSION['erreurPasNombreEntre']=0;
                header("Location: menuutilisateur.php");
                exit;
              }
              catch (PDOException $e){
                echo $e->getMessage();
              }
            }
          }
        }
        catch (PDOException $e){
            echo $e->getMessage();
        }
      }
      else {
        return "
        <div class='alert alert-warning'>
        <strong> Inscription impossible: </strong>Certains champs sont vides.
        </div>";
      }
    }
  }


  inscription();

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
    <!-- Récupération de la feuille de style du dossier -->
    <link rel="stylesheet" type="text/css" href="style.css">
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
      <form method="POST" action="menuinscription.php">
        <p>Inscription</p><br>
        <label>Choisissez votre pseudo: </label><input type="text" name="pseudoNew"><br>
        <label>Choisissez votre mot de passe: </label><input type="password" name="mdpNew"><br>
        <label>Entrez votre état civil: </label><br>
        <input type="radio" name="gender" value="homme" selected>Homme<br>
        <input type="radio" name="gender" value="femme">Femme<br>
        <label>Entrez vos coordonnées bancaires: </label><input type="text" name="cardNew"><br>
        <label>Choisissez votre photo de profil: </label><input type="file" name="pictureNew"><br>
        <button type="submit" class="btn btn-primary" name="inscription">Valider</button>
        <p> <?php echo inscription() ?> </p>
      </form>
    </div>

  </body>

  </html>

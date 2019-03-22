<?php
	session_start();

	if(isset($_POST['submitGraph'])){
		$pdo = new PDO('sqlite:'.dirname(__FILE__).'/.htbase.db');

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
        	if(isset($_POST[$i['nom']])){
        		try{
		          $idU = $_SESSION['id'];

		          $rq = 'INSERT INTO Action(id_user,id_entreprise) values (:idU,:idE);';
		          $request = $pdo->prepare($rq);
		          $request->execute(array(':idU' => $_SESSION['id'], 'idE' => $i['id']));
		        }
		        catch (PDOException $e){
		          echo $e->getMessage();
		        }
		    }
		}
	}

	header('Location: menuutilisateur.php');
?>

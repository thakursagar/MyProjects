<?php
	include "dbconnect.php";
	if(empty($_POST["EmailID"]) or empty($_POST["Password"])){
		echo -2;
		return;
	}
	$emailId=$_POST["EmailID"];
	$password=$_POST["Password"];
	$conn=mysql_connect($dbHost,$dbUserName,$dbPass);
	mysql_select_db($db_Name,$conn);
	mysql_query("CALL AuthenticateLogin('".$emailId."', '".$password."', @outResult)");
	$result=mysql_query("SELECT @outResult as outResult");
	mysql_close();
	if(empty($result)){
		echo -1;
		return;
	}
	else{
		$result=mysql_fetch_assoc($result);
		if($result['outResult']!=0){
			if(!isset($_SESSION['User'])){
				session_start();
				$_SESSION['User']=$result['outResult'];
			}
			//echo "hi";
			//echo $_SESSION['User'];
		}
		echo $result['outResult'];
	}
?>

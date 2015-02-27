<?php
	session_start();
	if(!isset($_SESSION['User']){
		return -3;
	}
	include "dbconnect.php";
	if(empty($_POST["HPID"]) or empty($_POST["UserID"])){
		return -2;
	}
	$hpId=$_POST["HPID"];
	$userId=$_POST["UserID"];
	$conn=mysql_connect($dbHost,$dbUserName,$dbPass);
	mysql_select_db($db_Name,$conn);
	mysql_query("CALL InsertReservation('".$hpId."', '".$userId."', @outResult)");
	$result=mysql_query("SELECT @outResult as outResult");
	mysql_close();
	if(empty($result)){
		return -1;
	}
	else{
		$result=mysql_fetch_assoc($result);	
		return $result['outResult'];
	}
?>
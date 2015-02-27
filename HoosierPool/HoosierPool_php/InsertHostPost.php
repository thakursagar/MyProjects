<?php
	session_start();
	if(session_id()!="User" && is_null($_SESSION['User'])){
		echo -3;
		return;
	}
	include "dbconnect.php";
	if(empty($_POST["UserID"]) or empty($_POST["Source"]) or empty($_POST["Destination"]) or empty($_POST["Price"]) or empty($_POST["Mobile"]) or empty($_POST["TotalMembers"]) 
	  or empty($_POST["Date"]) or empty($_POST["Time"]) or empty($_POST["PickupLocation"])){
		echo -2;
		return;
	}
	$userId=$_POST["UserID"];
	$source=$_POST["Source"];
	$destination=$_POST["Destination"];
	$price=$_POST["Price"];
	$mobile=$_POST["Mobile"];
	$totalmembers=$_POST["TotalMembers"];
	$date=$_POST["Date"];
	$time=$_POST["Time"];
	$pickuplocation=$_POST["PickupLocation"];
	$conn=mysql_connect($dbHost,$dbUserName,$dbPass);
	mysql_select_db($db_Name,$conn);
	mysql_query("CALL InsertHostPost('".$userId."', '".$source."', '".$destination."', '".$price."','".$mobile."','".$date."', '".$totalmembers."', '".$time."',@outResult,'".$pickuplocation."')");
	$result=mysql_query("SELECT @outResult as outResult");
	mysql_close();
	if(empty($result)){
		echo -1;
		return;
	}
	else{
		echo 1;
		return;
		$result=mysql_fetch_assoc($result);	
		echo $result['outResult'];
	}
?>
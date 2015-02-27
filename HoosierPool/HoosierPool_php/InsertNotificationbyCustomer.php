<?php
	session_start();
	if(!isset($_SESSION['User']){
		return -3;
	}
	include "dbconnect.php";
	if(empty($_POST["HPID"]) or empty($_POST["HostID"]) or empty($_POST["CustomerID"] )){
		return -2;
	}
	$hpId=$_POST["HPID"];
	$hostId=$_POST["HostID"];
	$customerId=$_POST["CustomerID"];
	$conn=mysql_connect($dbHost,$dbUserName,$dbPass);
	mysql_select_db($db_Name,$conn);
	mysql_query("CALL InsertNotificationbyCustomer('".$hpId."', '".$hostId."', '".$customerId."', @outResult)");
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
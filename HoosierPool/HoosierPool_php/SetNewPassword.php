<?php
	include "dbconnect.php";
	if(empty($_POST["EmailID"]) or empty($_POST["NewPassword"])){
		return -2;
	}
	$emailId=$_POST["EmailID"];
	$newPassword=$_POST["NewPassword"];
	$conn=mysql_connect($dbHost, $dbUserName, $dbPass);
	mysql_select_db($db_Name,$conn);
	mysql_query("CALL SetNewPassword('".$emailId."', '".$password."', @outResult)");
	$result=mysql_query("SELECT @outResult as outResult");
	mysql_close();
	if(empty($result)){
		return -1;
	}
	else{
		$result=mysql_fetch_assoc($result);	
		return 
		$result['outResult'];
	}
?>
<?php
	include "dbconnect.php";
	if(empty($_POST["EmailID"]) or empty($_POST["Password"]) or empty($_POST["SecurityQuestion"]) or empty($_POST["Answer"])){
		return -2;
	}
	$emailId=$_POST["EmailID"];
	$password=$_POST["Password"];
	$securityQuestion=$_POST["SecurityQuestion"];
	$answer=$_POST["Answer"];
	$conn=mysql_connect($dbHost, $dbUserName, $dbPass);
	mysql_select_db($db_Name,$conn);
	mysql_query("CALL RegisterUser('".$emailId."', '".$password."', '".$securityQuestion."', '".$answer."', @outResult)");
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

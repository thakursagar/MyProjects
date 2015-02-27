<?php
	include "dbconnect.php";
	if(empty($_POST["EmailID"])){
		return -2;
	}
	$emailId=$_POST["EmailID"];
	$conn=mysql_connect($dbHost, $dbUserName, $dbPass);
	mysql_select_db($db_Name,$conn);
	mysql_query("CALL RegisteredEmail('".$emailId."', @outResult)");
	$result=mysql_query("SELECT @outResult as outResult");
	mysql_close();
	if(empty($result)){
		echo -1;
	}
	else{
		$result=mysql_fetch_assoc($result);	
		echo $result['outResult'];
	}
?>
<?php
	include "dbconnect.php";
	if(empty($_POST["EmailID"])){
		return -2;
	}
	$emailId=$_POST["EmailID"];
	$conn=mysql_connect($dbHost,$dbUserName,$dbPass);
	mysql_select_db($db_Name,$conn);
	$result=mysql_query("CALL GetSecurityInfo('".$emailId."')");
	mysql_close();
	if(empty($result)){
		return -1;
	}
	else{
		$resultArray= array();
		while ($row = mysql_fetch_assoc($result)) {
			$resultArray[] = $row;
		}
		return json_encode($resultArray);
	}
?>
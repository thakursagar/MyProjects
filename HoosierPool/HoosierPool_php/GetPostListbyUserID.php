<?php
	/*session_start();
	if(session_id()!="User" && is_null($_SESSION['User'])){
		echo -3;
		return;
	}
	if(empty($_POST["UserID"])){
		echo -2;
		return;
	}*/
	include "dbconnect.php";
	$userId="1";//$_POST["UserID"];
	$conn=mysql_connect($dbHost,$dbUserName,$dbPass);
	mysql_select_db($db_Name,$conn);
	$resultCities=mysql_query("Select * from cities");
	$resultStates=mysql_query("Select * from states");
	$arrayCitiesCityName=array();
	$arrayCitiesStateID=array();
	$arrayStates=array();
	while($rowStates=mysql_fetch_assoc($resultStates)){
		$arrayStates[$rowStates["StateID"]]=$rowStates["StateName"];
		//echo $arrayStates[$rowStates["StateID"]];
	}
	while($rowCities=mysql_fetch_assoc($resultCities)){
		$arrayCitiesCityName[$rowCities["CityID"]]=$rowStates["CityName"];
		$arrayCitiesStateID[$rowCities["CityID"]]=$rowStates["StateID"];
		//echo $arrayCitiesCityName[$rowCities["CityID"]];
		//echo $arrayCitiesStateID[$rowCities["CityID"]];
	}
	$result=mysql_query("CALL GetPostListbyUserID('".$userId."')");
	echo $arrayCitiesCityName["1"];
	echo "hi";
	//$resultArray=array();
	/*while ($row = mysql_fetch_assoc($result)) {
		echo $row;
		
		//echo $arrayCitiesCityName[$row[1]];
		//$newrow=array($row[0], $arrayCitiesCityName[$row[1]]."(".$arrayStates[$arrayCitiesStateID[$row[1]]].")", $arrayCitiesCityName[$row[2]]."(".$arrayStates[$arrayCitiesStateID[$row[2]]].")", row[3], row[4]);
		//$row[1]=$arrayCitiesCityName[$row[1]]."(".$arrayStates[$arrayCitiesStateID[$row[1]]].")";
		//$row[2]=$arrayCitiesCityName[$row[2]]."(".$arrayStates[$arrayCitiesStateID[$row[2]]].")";
		//$resultArray[] = $newrow;
	}*/
	//echo json_encode($resultArray);
?>
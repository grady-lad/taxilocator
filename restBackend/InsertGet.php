<?php


$con = mysql_connect("closest-taxis.com","root","martin");
if (!$con)
  {
  die('Could not connect: ' . mysql_error());
  }

  else {

  echo "connected";

  }

  $names = $_GET["id"];
   $lat = $_GET["latt"];
  $lng = $_GET["lngg"]

  mysql_select_db("TaxiList", $con);

  mysql_query("INSERT INTO `Tax`(`Name`, `latitude`, `longitude`) VALUES ($names, $lat , $lng)
                ON DUPLICATE KEY UPDATE `latitude` = $lat , `longitude` = $lng");



mysql_error($con);


mysql_close($con);


?>







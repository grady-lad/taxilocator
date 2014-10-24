<?php


$con = mysql_connect("closest-taxis.com","root","martin");
if (!$con)
  {
  die('Could not connect: ' . mysql_error());
  }

$UserID = $_GET["ID"];
$UserPass = $_GET["Pass"];

mysql_select_db("TaxiList", $con);

$query = mysql_query("SELECT `UserName` ,`Password` FROM `Tax` WHERE `UserName` = '$UserID' AND `Password` = '$UserPass'");
if(mysql_affected_rows()>0){

echo "1";

}
else
{
echo "2";
}



?>
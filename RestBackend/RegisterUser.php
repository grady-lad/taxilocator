<?php

$con = mysql_connect("closest-taxis.com","root","martin");
if (!$con)
  {
  die('Could not connect: ' . mysql_error());
  }

$UserID = $_REQUEST['ID'];
$FirstName = $_REQUEST['FName'];
$LastName = $_REQUEST['LName'];
$UserPhone = $_REQUEST['Phone'];
$UserPass = $_REQUEST['Pass'];


mysql_select_db("TaxiList", $con);

$query = mysql_query("INSERT INTO `Tax`(`FirstName`, `LastName` , `UserName`, `PhoneNum`, `Password`) VALUES ('$FirstName','$LastName' ,'$UserID','$UserPhone','$UserPass')");



if(mysql_affected_rows()>0){

echo "1";

}
else
{
echo "2";
}


mysql_close($con);
?>
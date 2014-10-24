<?php
$con = mysql_connect("closest-taxis.com","root","martin");
if (!$con)
  {
  die('Could not connect: ' . mysql_error());
  }



$response = array();
$posts = array();
$UserLat = $_GET["Lat"];
$UserLng = $_GET["Lng"];

echo "alaright";

mysql_select_db("TaxiList", $con);
$result = mysql_query("SELECT FirstName, LastName, PhoneNum, latitude, longitude, ( 3959 * acos( cos( radians('$UserLat') ) * cos( radians( latitude ) )
                        * cos( radians( longitude ) - radians('$UserLng') ) + sin( radians('$UserLat') )
                        * sin( radians( latitude ) ) ) ) AS distance FROM Tax HAVING distance < 1");
while($row = mysql_fetch_array($result)){


    $fName =$row['FirstName'];
    $lName = $row['LastName'];
    $Phone = $row['PhoneNum'];
	$lat =$row['latitude'];
	$lng =$row['longitude'];

echo $lat;
echo "   ";
echo $lng;



$posts[] = array('Latitude'=> $lat, 'lontitude'=> $lng, 'FName' => $fName, 'LName' => $lName, 'PhoneNum' => $Phone);


	}

	mysql_close($con);


$response['posts'] = $posts;

$fp = fopen("results.json", "w");
fwrite($fp, json_encode($response));
fclose($fp);

//$_POST['results.json']




?>


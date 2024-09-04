<?php 

session_start();

    $_SESSION;
    $user_data = check_login($con);

  //  require_once "C:\Users\Lenovo\OneDrive\Documents\CODING\API_FOKUS\login";

//$database = new Database();
//$db = $database->connect();

//$query = $db->query("SELECT * FROM ");
   // $results = $query-> fetchAll(PDO: :FETCH_ASSOC);


    //diplayy

//foreach($results as $row) {
//echo 'ID: ' .$row['id']. ' - Name: ' . $row['fullname']. '<br>' ;
 //   }
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    
</head>
<body>
    <a href="logout.php"> Logout </a>
    <h1>Login Page</h1>
    <br> 
    Hello, username:
</body>
</html>
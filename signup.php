<?php
session_start();
$_SESSION;

include("connection.php");
include("functions.php");

    $user_data = check_login($con);

if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    //Data Retrieval

    $user_name = $_POST['username'];
    $password = $_POST['password']; 
    $fullname = $_POST['fullname'];   
    $email = $_POST['email'];       

    // Check if POST data is available
    $user_name = $_POST['user_name'];
    $password = $_POST['password']; // Fixed typo: 'passwprd' to 'password'

    if (!empty($user_name) && !empty($password) && !is_numeric($user_name)) 
    {

        //save to database
        $user_id = random_num(20);
        $query = "insert into users (id, fullname,username, password, email) values ('$id', '$fullname', '$username' '$password', '$email')";

        mysqli_query($con, $query);

        header("Location: login.php");
        die;
    }else
    {

        echo "Enter some valid information!";
    }
}
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign Up</title>
    <style type="text/css">
        body {
            font-family: Arial, sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            background-color: #f2f2f2;
        }
        .signup-container {
            padding: 20px;
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 300px;
        }
        .signup-container input[type="text"],
        .signup-container input[type="password"],
        .signup-container input[type="email"],
        .signup-container input[type="submit"] {
            width: 100%;
            padding: 10px;
            margin: 8px 0;
            border: 1px solid #ccc;
            border-radius: 3px;
        }
        .signup-container input[type="submit"] {
            background-color: #4CAF50;
            color: white;
            cursor: pointer;
        }
        .signup-container input[type="submit"]:hover {
            background-color: #45a049;
        }
        .signup-container a {
            display: block;
            text-align: center;
            margin-top: 10px;
            color: #4CAF50;
            text-decoration: none;
        }
        .signup-container a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="signup-container">
        <form action="signup.php" method="post">
            <input type="text" name="fullname" placeholder="Full Name" required>
            <input type="text" name="username" placeholder="Username" required>
            <input type="password" name="password" placeholder="Password" required>
            <input type="email" name="email" placeholder="Email" required>
            <input type="submit" value="Sign Up">
        </form>
        <a href="login.php">Already have an account? Login</a>
    </div>
</body>
</html>
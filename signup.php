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

    //for implementation
    <div class="signup-container">
        <form action="signup.php" method="post">
            <input type="text" name="fullname" placeholder="Full Name" required>
            <input type="text" name="username" placeholder="Username" required>
            <input type="password" name="password" placeholder="Password" required>
            <input type="email" name="email" placeholder="Email" required>
            <input type="submit" value="Sign Up">
        </form>
        <a href="login.php">Already have an account? Login</a>
    </div

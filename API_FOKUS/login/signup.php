<?php
session_start();
include("connection.php");
include("functions.php");

if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    // Data Retrieval
    $user_name = $_POST['username'] ?? ''; 
    $password = $_POST['password'] ?? ''; 
    $fullname = $_POST['fullname'] ?? ''; 
    $email = $_POST['email'] ?? ''; 

    if (!empty($user_name) && !empty($password) && !is_numeric($user_name)) {

        // Hash password
        $hashed_password = password_hash($password, PASSWORD_BCRYPT);

        // Save to database
        $query = "INSERT INTO users (fullname, username, password, email) VALUES (?, ?, ?, ?)";
        if ($stmt = mysqli_prepare($con, $query)) {
            mysqli_stmt_bind_param($stmt, "ssss", $fullname, $user_name, $hashed_password, $email);
            mysqli_stmt_execute($stmt);
            mysqli_stmt_close($stmt);

            header("Location: login.php");
            exit();
        }
    } else {
        echo "Enter valid information!";
    }
}


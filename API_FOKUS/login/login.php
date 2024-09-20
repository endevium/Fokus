<?php
session_start();
include("connection.php");
include("functions.php");

// Check if user is already logged in
$user_data = check_login($con);
if ($user_data) {
    header('Location: index.php');
    exit();
}

if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    // Data Retrieval
    $user_name = $_POST['username'] ?? ''; 
    $password = $_POST['password'] ?? ''; 

    if (!empty($user_name) && !empty($password) && !is_numeric($user_name)) {

        // Query to fetch the user details
        $query = "SELECT * FROM users WHERE username = ? LIMIT 1";
        if ($stmt = mysqli_prepare($con, $query)) {
            mysqli_stmt_bind_param($stmt, "s", $user_name);
            mysqli_stmt_execute($stmt);
            $result = mysqli_stmt_get_result($stmt);

            if ($result && mysqli_num_rows($result) > 0) {
                $user_data = mysqli_fetch_assoc($result);

                // Check if the password matches
                if (password_verify($password, $user_data['password'])) {
                    $_SESSION['user_id'] = $user_data['user_id'];
                    header("Location: index.php");
                    exit();
                } else {
                    echo "Incorrect password!";
                }
            } else {
                echo "Wrong username or enter a valid username";
            }

            mysqli_stmt_close($stmt);
        }
    } else {
        echo "Enter valid information!";
    }
}


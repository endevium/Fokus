<?php
session_start();
include("connection.php");
include("functions.php");

// Check if user is already logged in
$user_data = check_login($con);
if ($user_data) {
    // Redirect to index if user is already logged in
    header('Location: index.php');
    exit();
}

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    //Data Retrieval
    $user_name = $_POST['username']; // Fixed: Correct variable name 'username'
    $password = $_POST['password'];
    $fullname = $_POST['fullname'] ?? '';  // Optional, as it's not part of the login process
    $email = $_POST['email'] ?? '';  // Optional, as it's not part of the login process

    // Check if POST data is valid
    if (!empty($user_name) && !empty($password) && !is_numeric($user_name)) {

        // Query to fetch the user details
        $query = "SELECT * FROM users WHERE username = '$user_name' LIMIT 1";
        $result = mysqli_query($con, $query);

        if ($result && mysqli_num_rows($result) > 0) {
            $user_data = mysqli_fetch_assoc($result);

            // Check if the password matches
            if ($user_data['password'] === $password) {
                $_SESSION['user_id'] = $user_data['user_id'];
                header("Location: index.php");
                die();
            } else {
                echo "Incorrect password!";
            }
        } else {
            echo "Wrong username or enter a valid username";
        }
    } else {
        echo "Enter valid information!";
    }
}
?>

//for separation
    <form action="login_process.php" method="post">
            <input type="text" name="username" placeholder="Username" required>
            <input type="password" name="password" placeholder="Password" required>
            <input type="submit" value="Login">
        </form>
        <a href="signup.php">Sign Up</a>

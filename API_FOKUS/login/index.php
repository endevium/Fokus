<?php
session_start();
include('connection.php');
include('functions.php');

// Check if user is logged in
$user_data = check_login($con);

if (!$user_data) {
    // If user is not logged in, redirect to login page
    header('Location: login.php');
    exit();
}

?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Index Page</title>
</head>
<body>
    <a href="logout.php">Logout</a>
    <h1>Index Page</h1>
    <br>
    Hello, <?php echo htmlspecialchars($user_data['username']); ?>!
</body>
</html>
